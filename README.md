# EleLoadingView

A beautiful loading view for android inspired by Ele.me and dribbble

![](./screenshots/screenshot.gif)

## how to use it 

### import

Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

Add the dependency
```
dependencies {
	compile 'com.github.yiyuanliu:EleLoadingView:1.2'
}
```

### use in layout

define a array

```
<array name="emojis">
	<item>@drawable/emoji1</item>
    <item>@drawable/emoji2</item>
	<item>@drawable/emoji3</item>
    <item>@drawable/emoji4</item>
</array>
```

add EleLoadingView into your layout

```
<com.yiyuanliu.eleloadingview.EleLoadingView
    android:id="@+id/loading"        
	android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:eleIconList="@drawable/food1"
    app:eleJumpHeight="84dp"
	/>

<com.yiyuanliu.eleloadingview.EleLoadingView
    android:id="@+id/loading2"        
	android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:eleIconList="@array/emojis"
    app:eleJumpHeight="84dp"
	/>
```

### use in code

```
EleLoadingView loading = (EleLoadingView) findViewById(R.id.loading);

final Drawable[] foods = new Drawable[]{
    getResources().getDrawable(R.drawable.food1),
	getResources().getDrawable(R.drawable.food2),
    getResources().getDrawable(R.drawable.food3),
    getResources().getDrawable(R.drawable.food4),
    getResources().getDrawable(R.drawable.food5),
};
loading.setIcon(foods);

//you can set one icon too
//loading.setIcon(getResources().getDrawable(R.drawable.food1));
```

### attribute

attribute|method|default
---------|------|----
eleIcon|setIcon(Drawable... icons)|
eleIconList|setIcon(Drawable... icons)|
eleIconWidth|setIconWidth(int width)<br/>setIconWidthDp(int iconWidthDp)|48dp
eleIconHeight|setIconHeight(int height)<br/>setIconHeightDp(int heightDp)|48dp
eleJumpHeight|setJumpHeight(int jumpHeight)<br/>setJumpHeightDp(int jumpHeightDp)|64dp
eleShadowMax|setShadowMax(float shadowMax)|1 
eleShadowMin|setShadowMin(float shadowMin)|0.3 
eleShadowColor|setShadowColor(float color)|#ffa0a0a0
eleRotate|setRotate(boolean rotate)|false
|setDuration(long duration)|280