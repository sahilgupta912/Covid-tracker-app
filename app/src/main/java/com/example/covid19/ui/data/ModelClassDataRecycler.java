package com.example.covid19.ui.data;

public class ModelClassDataRecycler {
    private String txtState;
    private String txtStateConfirm;
    private String txtStateRecover;
    private String txtStateDeceased;

    public ModelClassDataRecycler(String txtState, String txtStateConfirm, String txtStateRecover, String txtStateDeceased) {
        this.txtState = txtState;
        this.txtStateConfirm = txtStateConfirm;
        this.txtStateRecover = txtStateRecover;
        this.txtStateDeceased = txtStateDeceased;
    }

    public String getTxtState() {
        return txtState;
    }

    public String getTxtStateConfirm() {
        return txtStateConfirm;
    }

    public String getTxtStateRecover() {
        return txtStateRecover;
    }

    public String getTxtStateDeceased() {
        return txtStateDeceased;
    }
}
