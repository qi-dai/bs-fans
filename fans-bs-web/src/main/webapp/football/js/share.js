var shareData;
wx.config({
	debug: false,
	appId: appId,
	timestamp: timestamp,
	nonceStr: nonceStr,
	signature: signature,
	jsApiList: [
		'onMenuShareTimeline',
		'onMenuShareAppMessage'
	]
});
wx.ready(function () {  
	wx.onMenuShareTimeline({
		title: '', // 分享标题
		link: '', // 分享链接
		imgUrl: '', // 分享图标
		success: function () { 
		},
		cancel: function () { 
			// 用户取消分享后执行的回调函数
		}
	});	  
	wx.onMenuShareAppMessage({
		title: '', // 分享标题
		desc: '', // 分享描述 
		link: '', // 分享链接
		imgUrl: '', // 分享图标
		type: '', // 分享类型,music、video或link，不填默认为link
		dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
		success: function () { 

		},
		cancel: function () { 
			// 用户取消分享后执行的回调函数
		}
	});
	shareData = {
		title: '森林湖 “电影票”大放送',
		desc: '六月观影季，森林湖邀您玩游戏免费赢取电影票，还在等什么！！',
		link: 'http://www.duanglife.com/football/index.php',
		imgUrl: 'http://www.duanglife.com/football/images/thumb.jpg'
	};
	wx.onMenuShareAppMessage(shareData);
	wx.onMenuShareTimeline(shareData);	
});