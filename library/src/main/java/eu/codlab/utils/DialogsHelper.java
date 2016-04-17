package eu.codlab.utils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.InputType;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gc.materialdesign.widgets.Dialog;
import com.gc.materialdesign.widgets.ProgressDialog;

import eu.codlab.library.R;

/**
 * Created by kevinleperf on 23/03/16.
 */
public final class DialogsHelper {

    private DialogsHelper() {

    }

    public static ProgressDialog createProgressDialog(@NonNull Context context,
                                                      @StringRes int title,
                                                      @ColorRes int color) {
        return new ProgressDialog(context, context.getString(title),
                context.getResources().getColor(color));

    }

    public static Dialog createValidationDialog(@NonNull Context context,
                                                @NonNull String title,
                                                @NonNull String content,
                                                @NonNull View.OnClickListener validation) {
        return createValidationDialog(context, title, content, validation, null);
    }

    public static Dialog createValidationDialog(@NonNull Context context,
                                                @NonNull String title,
                                                @NonNull String content,
                                                @NonNull View.OnClickListener validation,
                                                @Nullable View.OnClickListener cancel) {
        Dialog dialog = new Dialog(context, title, content);
        dialog.setOnAcceptButtonClickListener(validation);
        if (cancel != null) {
            dialog.addCancelButton(context.getString(R.string.cancel));
            dialog.setOnCancelButtonClickListener(cancel);
        }

        return dialog;
    }

    public static MaterialDialog
    createSingleChoiceDialog(@NonNull Context context,
                             @NonNull String title,
                             @NonNull String content,
                             @NonNull String[] choices,
                             @NonNull MaterialDialog.ListCallbackSingleChoice listener) {

        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .items(choices)
                .itemsCallbackSingleChoice(-1, listener)
                .negativeText(context.getText(R.string.cancel))
                .positiveText(context.getText(R.string.validate))
                .positiveColor(context.getResources().getColor(R.color.black))
                .build();
    }

    public static MaterialDialog
    createInputDialog(@NonNull Context context,
                      @NonNull String title,
                      @NonNull String content,
                      @NonNull String input,
                      @NonNull MaterialDialog.InputCallback listneer) {

        return new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .inputRange(1, 100)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .negativeText(context.getText(R.string.cancel))
                .positiveText(context.getText(R.string.validate))
                .positiveColor(context.getResources().getColor(R.color.black))
                .input("", input, listneer)
                .build();
    }
}
