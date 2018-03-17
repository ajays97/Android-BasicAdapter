# Android-BasicAdapter

BasicAdapter to use instead of separate java files for Android RecyclerView Adapters, with a more comprehensible adapter structure and databinding.

[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![Release](https://jitpack.io/v/ajays97/Android-BasicAdapter.svg)](https://jitpack.io/#ajays97/Android-BasicAdapter)
## Download from the jitpack repo
#### Add it to your root project build.gradle file at the end of repositories
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
#### Add the dependency
```
compile 'com.github.ajays97:Android-BasicAdapter:1.0'
```
## Use Android-BasicAdapter
```java
ArrayList<Post> posts = new ArrayList<>();        
posts.add(new Post("Ajay"));
posts.add(new Post("Srinivas"));

RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.setAdapter(BasicAdapter.with(R.layout.item, posts, new BasicAdapter.Binder<Post, ItemBinding>() {
    @Override
    public void onBind(int position, Post model, ItemBinding binding) {
        binding.text.setText(model.username);
    }
}));

// Using SmartBinder
recyclerView.setAdapter(BasicAdapter.with(R.layout.item, posts, new BasicAdapter.SmartBinder<Post, ItemBinding>() {
    @Override
    public void onBind(int position, Post model, ItemBinding binding) {
        binding.text.setText(model.username);
    }

    @Override
    public void onClick(View view, Post model, int position) {
        super.onClick(view, model, position);
    }

    @Override
    public void onCheckedChange(View view, Post model, int position) {
        super.onCheckedChange(view, model, position);
    }
}).setClickableViews(R.id.text).setCheckableViews(R.id.checkbox));
```

### License Information
```
Copyright 2018 Ajay Srinivas

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
