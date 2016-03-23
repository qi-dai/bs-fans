package mongo;

import com.eden.fans.bs.domain.enu.PostStatus;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by shengyanpeng on 2016/3/22.
 */
public class PostStatusAdapter extends TypeAdapter<PostStatus>{
    @Override
    public void write(JsonWriter jsonWriter, PostStatus postStatus) throws IOException {

    }

    @Override
    public PostStatus read(JsonReader jsonReader) throws IOException {
        //if(jsonReader.peek() ==  )
        return null;
    }
}
