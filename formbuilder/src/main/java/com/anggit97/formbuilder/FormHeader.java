package com.anggit97.formbuilder;

import android.widget.LinearLayout;

public class FormHeader extends FormObject {
    private String title;
    private LinearLayout.LayoutParams params;

    public FormHeader() {
    }

    public String getTitle() {
        return title;
    }

    public FormHeader setTitle(String title) {
        this.title = title;
        return this;
    }

    public LinearLayout.LayoutParams getParams() {
        return params;
    }

    public FormHeader setParams(LinearLayout.LayoutParams params) {
        this.params = params;
        return this;
    }
}