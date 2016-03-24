package com.eden.fans.bs.web.aop;

import com.eden.fans.bs.domain.annotation.ActionInput;
import com.eden.fans.bs.domain.annotation.ActionInputSign;
import com.eden.fans.bs.domain.annotation.InvalidInput;
import com.eden.fans.bs.domain.response.ResponseCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 入参实体类超类
 */
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = -3566985890949741543L;

	protected final static Logger LOGGER = LoggerFactory.getLogger(BaseEntity.class);
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/* get方法(public(self|extends)) */
	public static final Map<Class<?>, Map<String, ActionInputNotNullMethod>> notNullInputGetterMethods = new HashMap<Class<?>, Map<String, ActionInputNotNullMethod>>();

	public static class ActionInputNotNullMethod {
		private Method method;
		private ResponseCode code;

		private ActionInputNotNullMethod(Method method, ResponseCode code) {
			this.method = method;
			this.code = code;
		}

		public Method getMethod() {
			return method;
		}

		public void setMethod(Method method) {
			this.method = method;
		}

		public ResponseCode getCode() {
			return code;
		}

		public void setCode(ResponseCode code) {
			this.code = code;
		}
	}

	protected BaseEntity() {
	}

	/**
	 * 初始化非空入参集合
	 * 
	 * @param clazz
	 */
	public static void init(final Class<?> clazz) {
		initNotNullInput(clazz);
		initVerifySign(clazz);
	}

	private static void initNotNullInput(final Class<?> clazz) {
		initNotNullInput(clazz, clazz);
	}

	/**
	 * 验证非空输入
	 * 
	 * @param clazz
	 */
	private static void initNotNullInput(final Class<?> clazz, final Class<?> toplevelClass) {
		if (clazz == Object.class || clazz == Void.class) {
			return;
		}
		// if (!BaseEntity.class.isAssignableFrom(clazz)) {
		// throw new IllegalArgumentException("argument clazz must be BaseEntity's subclass");
		// }

		BaseEntity.initNotNullInput(clazz.getSuperclass(), toplevelClass);

		if (!notNullInputGetterMethods.containsKey(toplevelClass)) {
			notNullInputGetterMethods.put(toplevelClass, new HashMap<String, ActionInputNotNullMethod>());
		}

		final Map<String, ActionInputNotNullMethod> notNullInputClassGetterMethods = notNullInputGetterMethods.get(toplevelClass);

		Field[] inputFields = clazz.getDeclaredFields();

		for (final Field f : inputFields) {
			ActionInput ai = f.getAnnotation(ActionInput.class);
			if (ai == null || !ai.notNull()) {
				continue;
			}
			final InvalidInput iia = f.getAnnotation(InvalidInput.class);
			try {
				Method getterMethod = clazz.getDeclaredMethod("get" + StringUtils.capitalize(f.getName()));
				if (!Modifier.isPublic(getterMethod.getModifiers()) || Modifier.isStatic(getterMethod.getModifiers())) {
					throw new IllegalArgumentException(String.format("%s.%s() must be declared public and non static.", clazz.getName(), getterMethod.getName()));
				}
				if (!notNullInputClassGetterMethods.containsKey(getterMethod)) {
					notNullInputClassGetterMethods.put(getterMethod.getName(), new ActionInputNotNullMethod(getterMethod, new ResponseCode() {
						@Override
						public String getCode() {
							return iia == null ? "101" : iia.excode();
						}

						@Override
						public String getMsg() {
							return iia == null ? "参数[" + f.getName() + "]不能为空" : iia.exmsg();
						}
					}));
				}
			} catch (SecurityException e) {
				throw new IllegalArgumentException(e);
			} catch (NoSuchMethodException e) {
				LOGGER.error("加载{}.{}对应方法失败", new Object[] { clazz.getName(), f.getName() }, e);
				throw new IllegalArgumentException(e);
			}
		}

		Method[] methods = clazz.getDeclaredMethods();
		for (final Method m : methods) {
			ActionInput ai = m.getAnnotation(ActionInput.class);
			if (ai == null) {// 没有注解使用父类注解
				continue;
			}
			if (!ai.notNull()) {// 设置可以为空使用子类覆盖的标识
				if (clazz == toplevelClass && notNullInputClassGetterMethods.containsKey(m.getName())) {
					notNullInputClassGetterMethods.remove(m.getName());
				}
				continue;
			}
			final InvalidInput iia = m.getAnnotation(InvalidInput.class);
			try {
				if (!Modifier.isPublic(m.getModifiers()) || Modifier.isStatic(m.getModifiers())) {
					throw new IllegalArgumentException(String.format("%s.%s() must be declared public and non static.", clazz.getName(), m.getName()));
				}
				if (!notNullInputClassGetterMethods.containsKey(m.getName())) {
					notNullInputClassGetterMethods.put(m.getName(), new ActionInputNotNullMethod(m, new ResponseCode() {
						@Override
						public String getCode() {
							return iia == null ? "101" : iia.excode();
						}

						@Override
						public String getMsg() {
							return iia == null ? "参数[" + StringUtils.uncapitalize(StringUtils.removeStart(m.getName(), "get")) + "]不能为空" : iia.exmsg();
						}
					}));
				}
			} catch (SecurityException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	public static final Map<Class<?>, Map<String, Method>> verifySignGetterMethods = new HashMap<Class<?>, Map<String, Method>>();

	/**
	 * 验证签名
	 * 
	 * @param clazz
	 */
	private static void initVerifySign(final Class<?> clazz) {
		if (clazz == Object.class || clazz == Void.class) {
			return;
		}
		// if (!BaseEntity.class.isAssignableFrom(clazz)) {
		// throw new IllegalArgumentException("argument clazz must be BaseEntity's subclass");
		// }

		// 先掉父类，以便子类覆盖
		BaseEntity.initVerifySign(clazz.getSuperclass());

		if (!verifySignGetterMethods.containsKey(clazz)) {
			verifySignGetterMethods.put(clazz, new HashMap<String, Method>());
		}

		final Map<String, Method> verifySignClassGetterMethods = verifySignGetterMethods.get(clazz);

		Field[] inputFields = clazz.getDeclaredFields();

		for (final Field f : inputFields) {
			ActionInputSign ai = f.getAnnotation(ActionInputSign.class);
			if (ai == null) {
				continue;
			}
			try {
				Method getterMethod = clazz.getDeclaredMethod("get" + StringUtils.capitalize(f.getName()));
				if (!Modifier.isPublic(getterMethod.getModifiers()) || Modifier.isStatic(getterMethod.getModifiers())) {
					throw new IllegalArgumentException(String.format("%s.%s() must be declared public and non static.", clazz.getName(), getterMethod.getName()));
				}
				verifySignClassGetterMethods.put(getterMethod.getName(), getterMethod);
			} catch (SecurityException e) {
				throw new IllegalArgumentException(e);
			} catch (NoSuchMethodException e) {
				LOGGER.error("加载{}.{}对应方法失败", new Object[] { clazz.getName(), f.getName() }, e);
				throw new IllegalArgumentException(e);
			}
		}

		Method[] methods = clazz.getDeclaredMethods();
		for (Method m : methods) {
			ActionInputSign ai = m.getAnnotation(ActionInputSign.class);
			if (ai == null) {
				continue;
			}
			try {
				if (!Modifier.isPublic(m.getModifiers()) || Modifier.isStatic(m.getModifiers())) {
					throw new IllegalArgumentException(String.format("%s.%s() must be declared public and non static.", clazz.getName(), m.getName()));
				}
				verifySignClassGetterMethods.put(m.getName(), m);
			} catch (SecurityException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	/**
	 * 获取非空验参get方法
	 * 
	 * @return
	 * @author fuzhao
	 * @email fuzhao@jd.com
	 */
	public static Map<String, ActionInputNotNullMethod> getterNotNullMethods(Class<?> clazz) {
		if (!notNullInputGetterMethods.containsKey(clazz)) {
			String errmsg = String.format("请在%s.class加入代码" + "static {\n" + "        BaseEntity.init(%s.class);\n" + "}", clazz.getName(), clazz.getSimpleName());
			throw new RuntimeException(errmsg);
		}
		return notNullInputGetterMethods.get(clazz);
	}

	/**
	 * 获取签名验参get方法
	 * 
	 * @return
	 */
	public static Map<String, Method> getterSignMethods(Class<?> clazz) {
		if (!verifySignGetterMethods.containsKey(clazz)) {
			String errmsg = String.format("请在%s.class加入代码" + "static {\n" + "        BaseEntity.init(%s.class);\n" + "}", clazz.getName(), clazz.getSimpleName());
			throw new RuntimeException(errmsg);
		}
		return verifySignGetterMethods.get(clazz);
	}
}
