package com.antipov.mvp_template.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.antipov.mvp_template.R;

import java.util.List;

/**
 * Created by Antipov on 20.08.2017.
 */

public class DialogUtils {

    public static void showSnackbar(AppCompatActivity activity, String msg) {
        Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView().getRootView(),
                msg, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbar.show();
    }

    public static void showSnackbar(MaterialDialog dialog, String msg) {
        Snackbar snackbar = Snackbar.make(dialog.getWindow().getDecorView(),
                msg, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        snackbar.show();
    }

    public static Snackbar showSnackbar(AppCompatActivity activity, String msg, String actionMasg, View.OnClickListener onClickListener, int length) {
        Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView().getRootView(),
                msg, length).setAction(actionMasg, onClickListener);
        View snackbarView = snackbar.getView();
        TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        return snackbar;
    }

    /**
     * Method to show Toast-message with text from resources, duration short.
     *
     * @param context context of the app.
     * @param message text from resources need to be shown in Toast-message.
     */
    public static void show(Context context, int message) {
        Toast mToast = null;
        if (message == 0) {
            return;
        }
        if (context != null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        if (mToast != null) {
            mToast.setText(context.getString(message));
            mToast.show();
        }
    }

    /**
     * Method to show Toast-message with given text, duration short.
     *
     * @param context context of the app.
     * @param message text need to be shown in Toast-message.
     */
    public static void show(Context context, String message) {
        Toast mToast = null;
        if (message == null) {
            return;
        }
        if (context != null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        if (mToast != null) {
            mToast.setText(message);
            mToast.show();
        }
    }

    /**
     * Method to show Toast-message with text from resources, duration long.
     *
     * @param context context of the app.
     * @param message text from resources need to be shown in Toast-message.
     */
    public static void showLong(Context context, int message) {
        Toast mToast = null;
        if (message == 0) {
            return;
        }
        if (context != null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        }
        if (mToast != null) {
            mToast.setText(context.getString(message));
            mToast.show();
        }
    }

    /**
     * Method to show Toast-message with text from resources, duration long.
     *
     * @param context context of the app.
     * @param message text from resources need to be shown in Toast-message.
     */
    public static void showLong(Context context, String message) {
        Toast mToast = null;
        if (message == null) {
            return;
        }
        if (context != null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        }
        if (mToast != null) {
            mToast.setText(message);
            mToast.show();
        }
    }

    /**
     * Method to show input dialog.
     *
     * @param context                 context of the app.
     * @param title                   text in title, null - create dialog without title
     * @param positiveOnClickListener initialize listener to get chosen item
     * @return dialog window with options
     */
    public static MaterialDialog.Builder createInputDialog(Context context,
                                                           int title,
                                                           String content,
                                                           int inputType,
                                                           boolean cancelable,
                                                           CharSequence hint,
                                                           CharSequence prefill,
                                                           String positiveText,
                                                           String negativeText,
                                                           MaterialDialog.InputCallback positiveOnClickListener) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .inputType(inputType)
                .cancelable(cancelable)
                .input(hint, prefill, positiveOnClickListener)
                .positiveText(positiveText)
                .negativeText(negativeText);
    }

    public static MaterialDialog.Builder createInputDialog(Context context,
                                                           String title,
                                                           String content,
                                                           int inputType,
                                                           CharSequence hint,
                                                           CharSequence prefill,
                                                           MaterialDialog.InputCallback positiveOnClickListener) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .inputType(inputType)
                .cancelable(false)
                .input(hint, prefill, positiveOnClickListener)
                .positiveText(R.string.ok);
    }

    /**
     * Method to show input dialog.
     *
     * @param context                 context of the app.
     * @param title                   text in title, null - create dialog without title
     * @param positiveOnClickListener initialize listener to get chosen item
     * @return dialog window with options
     */
    public static MaterialDialog.Builder createInputDialog(Context context,
                                                           String title,
                                                           String content,
                                                           int inputType,
                                                           boolean cancelable,
                                                           CharSequence hint,
                                                           CharSequence prefill,
                                                           MaterialDialog.InputCallback positiveOnClickListener) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .inputType(inputType)
                .cancelable(cancelable)
                .input(hint, prefill, positiveOnClickListener)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel);
    }

    public static MaterialDialog.Builder createCustomDialog(Context context,
                                                            String title,
                                                            int layout,
                                                            boolean autoDismiss,
                                                            MaterialDialog.SingleButtonCallback positiveOnClickListener,
                                                            String negativeText,
                                                            String positiveText) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .customView(layout, true)
                .autoDismiss(autoDismiss)
                .onPositive(positiveOnClickListener)
                .negativeText(negativeText)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .positiveText(positiveText);
    }

    /**
     * Method to show single choice dialog.
     *
     * @param context                     context of the app.
     * @param title                       text in title, null - create dialog without title
     * @param itemsArray                  items in dialog to choose
     * @param singleChoiceOnClickListener initialize listener to get chosen item
     * @param index                       selected item
     * @return dialog window with options
     */
    public static MaterialDialog.Builder createSingleChoiceItemsDialog(Context context,
                                                                       String title,
                                                                       List<String> itemsArray,
                                                                       int index,
                                                                       MaterialDialog.ListCallbackSingleChoice singleChoiceOnClickListener) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .items(itemsArray)
                .itemsCallbackSingleChoice(index, singleChoiceOnClickListener);
    }

    /**
     * Method to show single choice dialog.
     *
     * @param context                 context of the app.
     * @param title                   text in title, null - create dialog without title
     * @param positiveOnClickListener initialize listener to get chosen item
     * @return dialog window with options
     */
    public static MaterialDialog.Builder createBasicDialog(Context context,
                                                           int title,
                                                           String content,
                                                           boolean cancelable,
                                                           MaterialDialog.SingleButtonCallback positiveOnClickListener,
                                                           MaterialDialog.SingleButtonCallback negativeOnClickListener,
                                                           String positiveText,
                                                           String negativeText) {
        return new MaterialDialog.Builder(context)
                .theme(Theme.LIGHT)
                .title(title)
                .content(content)
                .cancelable(cancelable)
                .onPositive(positiveOnClickListener)
                .onNegative(negativeOnClickListener)
                .positiveText(positiveText)
                .negativeText(negativeText);
    }

    /**
     * Method to show single choice dialog.
     *
     * @param context                 context of the app.
     * @param title                   text in title, null - create dialog without title
     * @param positiveOnClickListener initialize listener to get chosen item
     * @return dialog window with options
     */
    public static MaterialDialog.Builder createBasicDialog(Context context,
                                                           int title,
                                                           String content,
                                                           boolean cancelable,
                                                           MaterialDialog.SingleButtonCallback positiveOnClickListener,
                                                           String positiveText,
                                                           String negativeText) {
        return new MaterialDialog.Builder(context)
                .theme(Theme.LIGHT)
                .title(title)
                .content(content)
                .cancelable(cancelable)
                .onPositive(positiveOnClickListener)
                .positiveText(positiveText)
                .negativeText(negativeText);
    }

    /**
     * Method to show informatoin dialog with just 'ok' button.
     *
     * @param context                 context of the app.
     * @param positiveOnClickListener initialize listener to get chosen item
     * @return dialog window with options
     */
    public static MaterialDialog.Builder createInformationDialog(Context context,
                                                                 String title,
                                                                 String content,
                                                                 boolean cancelable,
                                                                 MaterialDialog.SingleButtonCallback positiveOnClickListener) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .cancelable(cancelable)
                .onPositive(positiveOnClickListener)
                .positiveText(R.string.ok);
    }

    /**
     * Method to show multiple choise dialog.
     *
     * @param context
     * @param title
     * @param cancelable
     * @param items
     * @param listCallbackMultiChoice
     * @return
     */
    public static MaterialDialog.Builder createMultipleChoiceDialog(Context context,
                                                                    String title,
                                                                    boolean cancelable,
                                                                    List items,
                                                                    Integer[] selectedIndexes,
                                                                    MaterialDialog.ListCallbackMultiChoice listCallbackMultiChoice) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .items(items)
                .cancelable(cancelable)
                .itemsCallbackMultiChoice(selectedIndexes, listCallbackMultiChoice)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel);
    }


}

