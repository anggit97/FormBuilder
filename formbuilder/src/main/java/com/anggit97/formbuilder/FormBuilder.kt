package com.anggit97.formbuilder

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

class FormBuilder(private val context: Context, private val linearLayout: LinearLayout) {
    var formMap: LinkedHashMap<String, FormElement> = LinkedHashMap()
    var viewMap: LinkedHashMap<String, View> = LinkedHashMap()
    private var selectedEditText: EditText? = null
    private var selectedFormElement: FormElement? = null
    private val calendar: Calendar = Calendar.getInstance()

    fun build(formObjects: List<FormObject?>) {
        for (formObject in formObjects) {
            when (formObject) {
                is FormHeader -> {
                    addToLinearLayout(
                        buildHeader(formObject),
                        formObject.params
                    )
                }
                is FormElement -> {
                    val tag = formObject.tagOrToString
                    formMap[tag] = formObject
                    addToLinearLayout(
                        buildElement(formObject),
                        formObject.params
                    )
                }
                is FormButton -> {
                    addToLinearLayout(
                        buildButton(formObject),
                        formObject.params
                    )
                }
            }
        }
    }

    // Builders
    private fun buildHeader(header: FormHeader): View {
        val headerTextView =
            TextView(context, null, R.attr.listSeparatorTextViewStyle)
        headerTextView.text = header.title
        return headerTextView
    }

    private val formElementChangeListeners =
        ArrayList<FormElementChangeListener>()

    fun addFormElementChangeListener(listener: FormElementChangeListener) {
        formElementChangeListeners.add(listener)
    }

    interface FormElementChangeListener {
        //To use, implement onFormElementChangeListener and call FormBuilder.addFormElementChangeListener(activity) from OnCreate()
        fun onFormElementChangeListener(element: FormElement?): FormElement?
    }

    private fun buildElement(formElement: FormElement): View? {
        val type = formElement.type
        val textInputLayout: TextInputLayout
        val editText = EditText(context)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
                formElement.value = charSequence.toString()
                //Notify listeners.
                for (listener in formElementChangeListeners) listener.onFormElementChangeListener(
                    formElement
                )
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        return when (type) {
            FormElement.Type.TEXT -> {
                textInputLayout = TextInputLayout(context)
                editText.isEnabled = formElement.enabled
                editText.hint = formElement.hint
                editText.setText(formElement.value)
                editText.inputType = InputType.TYPE_CLASS_TEXT
                editText.setText(formElement.value)
                if (!formElement.singleLine) {
                    editText.isSingleLine = false
                    editText.imeOptions = EditorInfo.IME_FLAG_NO_ENTER_ACTION
                }
                viewMap[formElement.tagOrToString] = editText
                addViewToView(textInputLayout, editText)
                textInputLayout
            }
            FormElement.Type.TEXTVIEW -> {
                editText.isEnabled = formElement.enabled
                editText.hint = formElement.hint
                editText.setText(formElement.value)
                editText.gravity = Gravity.START or Gravity.TOP
                editText.inputType = InputType.TYPE_CLASS_TEXT
                editText.setText(formElement.value)
                viewMap[formElement.tagOrToString] = editText
                editText
            }
            FormElement.Type.EMAIL -> {
                textInputLayout = TextInputLayout(context)
                editText.isEnabled = formElement.enabled
                editText.hint = formElement.hint
                editText.setText(formElement.value)
                editText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                editText.setText(formElement.value)
                viewMap[formElement.tagOrToString] = editText
                addViewToView(textInputLayout, editText)
                textInputLayout
            }
            FormElement.Type.PHONE -> {
                textInputLayout = TextInputLayout(context)
                editText.isEnabled = formElement.enabled
                editText.hint = formElement.hint
                editText.setText(formElement.value)
                editText.inputType = InputType.TYPE_CLASS_PHONE
                editText.setText(formElement.value)
                viewMap[formElement.tagOrToString] = editText
                addViewToView(textInputLayout, editText)
                textInputLayout
            }
            FormElement.Type.PASSWORD -> {
                textInputLayout = TextInputLayout(context)
                editText.isEnabled = formElement.enabled
                editText.hint = formElement.hint
                editText.setText(formElement.value)
                editText.inputType = InputType.TYPE_CLASS_TEXT
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                editText.setText(formElement.value)
                viewMap[formElement.tagOrToString] = editText
                addViewToView(textInputLayout, editText)
                textInputLayout
            }
            FormElement.Type.NUMBER -> {
                textInputLayout = TextInputLayout(context)
                editText.isEnabled = formElement.enabled
                editText.hint = formElement.hint
                editText.setText(formElement.value)
                editText.inputType = InputType.TYPE_CLASS_NUMBER
                editText.setText(formElement.value)
                viewMap[formElement.tagOrToString] = editText
                addViewToView(textInputLayout, editText)
                textInputLayout
            }
            FormElement.Type.URL -> {
                textInputLayout = TextInputLayout(context)
                editText.isEnabled = formElement.enabled
                editText.hint = formElement.hint
                editText.setText(formElement.value)
                editText.inputType = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
                editText.setText(formElement.value)
                viewMap[formElement.tagOrToString] = editText
                addViewToView(textInputLayout, editText)
                textInputLayout
            }
            FormElement.Type.ZIP -> {
                textInputLayout = TextInputLayout(context)
                editText.isEnabled = formElement.enabled
                editText.hint = formElement.hint
                editText.setText(formElement.value)
                editText.inputType = InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS
                editText.setText(formElement.value)
                viewMap[formElement.tagOrToString] = editText
                addViewToView(textInputLayout, editText)
                textInputLayout
            }
            FormElement.Type.DATE -> {
                textInputLayout = TextInputLayout(context)
                val dateEditText = EditText(context)
                dateEditText.isFocusable = false
                dateEditText.isClickable = true
                dateEditText.isEnabled = formElement.enabled
                dateEditText.hint = formElement.hint
                dateEditText.setText(formElement.value)
                dateEditText.inputType = InputType.TYPE_CLASS_TEXT
                dateEditText.setOnClickListener {
                    selectedFormElement = formElement
                    pickDate(dateEditText)
                }
                dateEditText.setText(formElement.value)
                viewMap[formElement.tagOrToString] = editText
                addViewToView(textInputLayout, dateEditText)
                textInputLayout
            }
            FormElement.Type.TIME -> {
                textInputLayout = TextInputLayout(context)
                val timeEditText = EditText(context)
                timeEditText.isFocusable = false
                timeEditText.isClickable = true
                timeEditText.isEnabled = formElement.enabled
                timeEditText.hint = formElement.hint
                timeEditText.setText(formElement.value)
                timeEditText.inputType = InputType.TYPE_CLASS_TEXT
                timeEditText.setOnClickListener {
                    selectedFormElement = formElement
                    pickTime(timeEditText)
                }
                timeEditText.setText(formElement.value)
                viewMap[formElement.tagOrToString] = editText
                addViewToView(textInputLayout, timeEditText)
                textInputLayout
            }
            FormElement.Type.MULTIPLE_SELECTION -> {
                textInputLayout = TextInputLayout(context)
                val multipleSelectionEditText = EditText(context)
                multipleSelectionEditText.isFocusable = false
                multipleSelectionEditText.isClickable = true
                multipleSelectionEditText.isEnabled = formElement.enabled
                multipleSelectionEditText.hint = formElement.hint
                multipleSelectionEditText.setText(
                    formElement.optionsSelected.toString().replace(
                        "[",
                        ""
                    ).replace("]", "")
                )
                multipleSelectionEditText.inputType = InputType.TYPE_CLASS_TEXT
                multipleSelectionEditText.setOnClickListener {
                    pickMultipleDialog(
                        multipleSelectionEditText,
                        formElement
                    )
                }
                viewMap[formElement.tagOrToString] = editText
                addViewToView(textInputLayout, multipleSelectionEditText)
                textInputLayout
            }
            FormElement.Type.SELECTION -> {
                textInputLayout = TextInputLayout(context)
                val selectionEditText = EditText(context)
                selectionEditText.isFocusable = false
                selectionEditText.isClickable = true
                selectionEditText.isEnabled = formElement.enabled
                selectionEditText.hint = formElement.hint
                selectionEditText.inputType = InputType.TYPE_CLASS_TEXT
                selectionEditText.setText(
                    formElement.optionsSelected.toString().replace(
                        "[",
                        ""
                    ).replace("]", "")
                )
                selectionEditText.setOnClickListener { pickDialog(selectionEditText, formElement) }
                viewMap[formElement.tagOrToString] = editText
                addViewToView(textInputLayout, selectionEditText)
                textInputLayout
            }
            else -> null
        }
    }

    private fun buildButton(formButton: FormButton): View {
        val button = Button(context)
        button.text = formButton.title

        formButton.backgroundColor?.let {
            button.setBackgroundResource(it)
        }

        formButton.textColor?.let {
            formButton.setTextColor(it)
        }

        formButton.runnable?.let {
            button.setOnClickListener { it.run {  } }
        }

        formButton.backgroundResource?.let {
            button.setBackgroundResource(it)
        }
        return button
    }

    private fun addToLinearLayout(
        view: View?,
        params: LinearLayout.LayoutParams?
    ) {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 8, 8, 8)
        view!!.layoutParams = params ?: layoutParams
        linearLayout.addView(view)
    }

    private fun addViewToView(parent: ViewGroup, child: View) {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        child.layoutParams = layoutParams
        parent.addView(child)
    }

    // Date picker
    fun pickDate(et: EditText?) {
        if (et != null) {
            selectedEditText = et
            DatePickerDialog(
                context, datePickerListener, calendar[Calendar.YEAR], calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private val datePickerListener = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = monthOfYear
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        val sdf = SimpleDateFormat(selectedFormElement?.dateFormat)
        if (selectedEditText != null) {
            selectedEditText?.setText(sdf.format(calendar.time))
            selectedFormElement?.value = sdf.format(calendar.time)
        }
    }

    // Time picker
    fun pickTime(et: EditText?) {
        if (et != null) {
            selectedEditText = et
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
            val minute = mcurrentTime[Calendar.MINUTE]
            val mTimePicker: TimePickerDialog
            mTimePicker =
                TimePickerDialog(context, timePickerListener, hour, minute, true) //Yes 24 hour time
            mTimePicker.show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private val timePickerListener = OnTimeSetListener { _, selectedHour, selectedMinute ->
        calendar[Calendar.HOUR] = selectedHour
        calendar[Calendar.MINUTE] = selectedMinute
        val sdf = SimpleDateFormat(selectedFormElement!!.timeFormat)
        if (selectedEditText != null) {
            selectedEditText!!.setText(sdf.format(calendar.time))
            selectedFormElement!!.value = sdf.format(calendar.time)
        }
    }

    // Choice
    private fun pickMultipleDialog(
        selectedEditText: EditText,
        selectedFormElement: FormElement
    ) {
        val selectedElements: MutableList<String> =
            ArrayList(selectedFormElement.optionsSelected)
        this.selectedEditText = selectedEditText
        val builder = AlertDialog.Builder(context)
        builder.setTitle("")
        builder.setMultiChoiceItems(
            selectedFormElement.options.toTypedArray<CharSequence>(),
            selectedFormElement.checkedValues
        ) { _, which, isChecked ->
            if (isChecked) {
                selectedElements.add(selectedFormElement.options[which])
            } else {
                selectedElements.remove(selectedFormElement.options[which])
            }
        }
        builder.setPositiveButton("OK") { _, _ ->
            selectedFormElement.optionsSelected = selectedElements
            selectedEditText.setText(
                selectedFormElement.optionsSelected.toString().replace(
                    "[",
                    ""
                ).replace("]", "")
            )
        }
        builder.setNegativeButton("Cancel") { dialogInterface, _ -> dialogInterface.dismiss() }
        builder.show()
    }

    private fun pickDialog(
        selectedEditText: EditText,
        selectedFormElement: FormElement
    ) {
        val selectedElements: MutableList<String> =
            ArrayList(selectedFormElement.optionsSelected)
        this.selectedEditText = selectedEditText
        val builder = AlertDialog.Builder(context)
        builder.setTitle("")
        builder.setSingleChoiceItems(
            selectedFormElement.options.toTypedArray<CharSequence>(),
            selectedFormElement.checkedValue,
            null
        )
        builder.setPositiveButton("OK") { dialogInterface, _ ->
            dialogInterface.dismiss()
            val selectedPosition =
                (dialogInterface as AlertDialog).listView
                    .checkedItemPosition
            selectedElements.clear() //We only want one input
            selectedElements.add(selectedFormElement.options[selectedPosition])
            selectedFormElement.optionsSelected = selectedElements
            selectedEditText.setText(
                selectedFormElement.optionsSelected.toString().replace(
                    "[",
                    ""
                ).replace("]", "")
            )
        }
        builder.setNegativeButton("Cancel") { dialogInterface, _ -> dialogInterface.dismiss() }
        builder.show()
    }

    // Validation
    fun validate(): Boolean {
        var isValid = true
        for ((_, element) in formMap) {
            val view = viewMap[element.tagOrToString]
            if (element.required) {
                if (element.type == FormElement.Type.MULTIPLE_SELECTION || element.type == FormElement.Type.SELECTION) {
                    if (element.optionsSelected == null || element.optionsSelected.isEmpty()) {
                        isValid = false
                        if (view is EditText) {
                            view.error = element.errorMessageOrDefault
                        }
                    }
                } else {
                    if (element.value == null || element.value.length == 0) {
                        isValid = false
                        if (view is EditText) {
                            view.error = element.errorMessageOrDefault
                        }
                    }
                }
            }
            if (element.formValidation != null) {
                val validation = element.formValidation.validate()
                if (!validation) {
                    isValid = validation
                    if (view is EditText) {
                        view.error = element.errorMessageOrDefault
                    }
                }
            }
        }
        return isValid
    }

}