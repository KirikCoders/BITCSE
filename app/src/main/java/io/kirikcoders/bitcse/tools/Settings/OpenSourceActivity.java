package io.kirikcoders.bitcse.tools.Settings;

import androidx.appcompat.app.AppCompatActivity;
import io.kirikcoders.bitcse.R;

import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by Akash on 26-Jan-19.
 */

public class OpenSourceActivity extends AppCompatActivity {
    ListView opensourcelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_source);
        opensourcelist=findViewById(R.id.open_lst);
        String[] name={"Glide","CircularImageView"};
        String[] url={"https://github.com/bumptech/glide","https://github.com/lopspower/CircularImageView"};
        String[] licence={"BSD, part MIT and Apache 2.0. See the LICENSE file for details.","CircularImageView by Lopez Mikhael is licensed under a Apache License 2.0. \n\n Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 \n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License."};
        OpenSourceModel adapter=new OpenSourceModel(this,name,url,licence);
        opensourcelist.setAdapter(adapter);
    }
}
