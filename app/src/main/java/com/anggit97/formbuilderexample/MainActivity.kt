package com.anggit97.formbuilderexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anggit97.formbuilder.FormBuilder
import com.anggit97.formbuilder.FormButton
import com.anggit97.formbuilder.FormElement
import com.anggit97.formbuilder.FormObject
import kotlinx.android.synthetic.main.activity_main.*

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
            FormButton().setBackgroundColor(R.color.colorPrimary).setTitle("Enter").setTextColor(R.color.colorWhite)
        )

        formBuilder.build(formElementMutableList)
    }
}
