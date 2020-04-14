# FORM BUILDER
Never this easy to easy to create form in Android

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

this library to create form in andorid

# INSTALLATION
Add this in your project's build.gradle file:
```sh
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Add this in your app's build.gradle file:
```sh
dependencies {
    implementation 'com.github.anggit97:FormBuilder:0.1.0'
}
```

# Usage
1. Create parent layout to accomodate views you want to show
```sh
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/rootLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity"/>
```
2. initialize FormBuilder Class in your actvity
```sh
private val formBuilder: FormBuilder by lazy {
    FormBuilder(this, rootLayout)
}
```
3. initialize list of form object
```sh
private val formElementMutableList = mutableListOf<FormObject>()
```
4. add form element to list of form object
```sh
formElementMutableList.add(
    FormElement().setTitle("Name").setType(FormElement.Type.TEXT).setHint("Enter your name")
)

formElementMutableList.add(
    FormElement().setTitle("Password").setType(FormElement.Type.PASSWORD).setHint("Enter your password")
)

formElementMutableList.add(
    FormElement().setTitle("Email").setType(FormElement.Type.EMAIL).setHint("Enter your email")
)

formElementMutableList.add(
    FormButton().setBackgroundColor(R.color.colorPrimary).setTitle("Enter")
)

```
5. add form element to FormBuilder build method
```sh
formBuilder.build(formElementMutableList)
```
6. Thats it, here full code
```sh
class MainActivity : AppCompatActivity() {

    private val formBuilder: FormBuilder by lazy {
        FormBuilder(this, rootLayout)
    }

    private val formElementMutableList = mutableListOf<FormObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        formElementMutableList.add(
            FormElement().setTitle("Name").setType(FormElement.Type.TEXT).setHint("Enter your name")
        )

        formElementMutableList.add(
            FormElement().setTitle("Password").setType(FormElement.Type.PASSWORD).setHint("Enter your password")
        )

        formElementMutableList.add(
            FormElement().setTitle("Email").setType(FormElement.Type.EMAIL).setHint("Enter your email")
        )

        formElementMutableList.add(
            FormButton().setBackgroundColor(R.color.colorPrimary).setTitle("Enter")
        )

        formBuilder.build(formElementMutableList)
    }
}
```
7. result
![Result 1](https://github.com/anggit97/FormBuilder/blob/master/screenshot/result1.jpg)

# Retrieve Values
Values inserted are saved inside a map of the object FormBuilder, using tags as key.
```sh
String textValue = formBuilder.formMap.get("tagKey").getValue();
```

# Validation
To make a validation simply call
```sh
val isValid = formBuilder.validate();
```
This will show an error on all forms that has been set as required.
It's possible to change error content on each form element.
```sh
formObjects.add(new FormElement()
      .setTag("tagKey")
      .setHint("Text")
      .setType(FormElement.Type.TEXT)
      .setErrorMessage("You can learn from this error"));
```
Every form element can accept a customized code for its validation.
```sh
final FormElement formElement = new FormElement().setTag("view").setHint("view").setType(FormElement.Type.TEXTVIEW));
formElement.setFormValidation(new FormValidation() {
				  @Override
				  public boolean validate() {
				      return formElement.getValue().length() > 5;
				  }
			      }
).setErrorMessage("Too short");
```

# Features!

  - Support create field like (TEXT, TEXTVIEW, EMAIL, PASSWORD, PHONE, NUMBER, URL, SPINNER,ZIP,SELECTION, MULTIPLE_SELECTION, DATE, TIME), Button, and Header
  - Support Field Validation

### Todos

 - Add styling on form element

License
----

MIT


**Free Software, Hell Yeah!**

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)


   [dill]: <https://github.com/joemccann/dillinger>
   [git-repo-url]: <https://github.com/joemccann/dillinger.git>
   [john gruber]: <http://daringfireball.net>
   [df1]: <http://daringfireball.net/projects/markdown/>
   [markdown-it]: <https://github.com/markdown-it/markdown-it>
   [Ace Editor]: <http://ace.ajax.org>
   [node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [jQuery]: <http://jquery.com>
   [@tjholowaychuk]: <http://twitter.com/tjholowaychuk>
   [express]: <http://expressjs.com>
   [AngularJS]: <http://angularjs.org>
   [Gulp]: <http://gulpjs.com>

   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]: <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>
   [PlMe]: <https://github.com/joemccann/dillinger/tree/master/plugins/medium/README.md>
   [PlGa]: <https://github.com/RahulHP/dillinger/blob/master/plugins/googleanalytics/README.md>
