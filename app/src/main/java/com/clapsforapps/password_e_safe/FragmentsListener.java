package com.clapsforapps.password_e_safe;

/**
 * Created by ALI on 1/4/2017.
 */

public interface FragmentsListener {
    void onPasswordSelected(Password password);
    void onViewPasswordEnded(boolean isDataSetChanged, String snackBarMsg, boolean requiresRefresh);
    void onPasswordAdded(String snackBarMsg);
    void onInsertEnded();
    void onInsertRequested(int type);
}
