package com.julian.lbniwkalkulator.fragments;

import static com.julian.lbniwkalkulator.util.ErrorHandler.processException;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.julian.lbniwkalkulator.R;
import com.julian.lbniwkalkulator.calculations.RadiationDataProcessor;
import com.julian.lbniwkalkulator.dataclasess.ExposureTime;
import com.julian.lbniwkalkulator.dataclasess.RadiationData;
import com.julian.lbniwkalkulator.exceptions.ExposureTimeTooLongException;
import com.julian.lbniwkalkulator.exceptions.InputNotSupportedException;
import com.julian.lbniwkalkulator.exceptions.InvalidRadiationDataTypeException;
import com.julian.lbniwkalkulator.exceptions.RadiationDataNotFoundException;
import com.julian.lbniwkalkulator.util.AppNotificationHandler;
import com.julian.lbniwkalkulator.util.AudioHandler;
import com.julian.lbniwkalkulator.util.StringGetter;

public class CalculatedTimeViewFragment extends Fragment {
    private static final String INPUT_DATA_ARGUMENT = "input_data";
    private static final int SOUND_TIME_MILLISECONDS = 10_000;
    private static final int TIMER_TICK_MILLISECONDS = 10;

    private boolean sendNotifications = false;
    private long timeRemaining;
    private boolean isCounting;
    private CountDownTimer countDownTimer;
    private ExposureTime exposureTime;
    private AppNotificationHandler notificationHandler;
    private AudioHandler audioHandler;

    public CalculatedTimeViewFragment() {
        super(R.layout.calculated_time_view_layout);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                             @Nullable Bundle savedInstanceState) {
        setExposureTime();
        handlePermissions(requireActivity());
        this.notificationHandler = new AppNotificationHandler(requireContext(), exposureTime);
        this.audioHandler = new AudioHandler(requireContext());
        setUpButton(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    protected void removeSelf() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void setUpButton(View rootView) {
        Button countDownButton = rootView.findViewById(R.id.count_down_button);
        timeRemaining = exposureTime.toMilliseconds();
        countDownButton.setText(exposureTime.toString());
        countDownButton.setOnClickListener(view -> {
            if (isCounting) {
                stopCountdown();
            } else {
                startCountdown(countDownButton);
            }
        });
    }

    private void sendNotificationsConditionally() {
        if(sendNotifications) {
            notificationHandler.updateTimeRemaining(timeRemaining);
            notificationHandler.sendNotification();
        }
    }

    private void startCountdown(Button countDownButton) {
        isCounting = true;
        if(timeRemaining == 0) return;
        sendNotificationsConditionally();
        setUpCountDownTimer(countDownButton);
    }

    private void setUpCountDownTimer(Button countDownButton) {
        final boolean[] soundMade = {false};
        countDownTimer = new CountDownTimer(timeRemaining, TIMER_TICK_MILLISECONDS) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                if(millisUntilFinished <= SOUND_TIME_MILLISECONDS && !soundMade[0]) {
                    soundMade[0] = true;
                    audioHandler.play(R.raw.warning_music);
                }
                try {
                    countDownButton.setText(ExposureTime.fromMilliseconds(timeRemaining).toString());
                } catch (InputNotSupportedException e) {
                    throw new RuntimeException("Something really bad happened in count down timer", e);
                }
            }
            @Override
            public void onFinish() {
                isCounting = false;
                countDownButton.setText(new ExposureTime().toString());
                cancelSoundAndNotification();
            }
        }.start();
    }

    private void cancelSoundAndNotification() {
        notificationHandler.cancelNotification();
        audioHandler.stop();
    }

    private void stopCountdown() {
        isCounting = false;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        cancelSoundAndNotification();
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    sendNotifications = true;
                } else {
                    sendNotifications = false;
                    Toast.makeText(this.requireContext(), StringGetter.
                                    fromStringsXML(R.string.notifications_denied),
                            Toast.LENGTH_SHORT).show();
                }
            });

    private void handlePermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        } else {
            sendNotifications = true;
        }
    }

    private void setExposureTime() {
        this.exposureTime = new ExposureTime();
        try {
            RadiationData data = requireArguments()
                    .getParcelable(INPUT_DATA_ARGUMENT, RadiationData.class);
            if(data == null) throw new RadiationDataNotFoundException(StringGetter.
                    fromStringsXML(R.string.exception_radiation_data_not_found_message));
            this.exposureTime = RadiationDataProcessor.processRadiationData(data);
        } catch (ExposureTimeTooLongException e) {
            processException(requireContext(),
                    e.getMessage(),
                    R.string.exception_too_long_exposure_time_additional,
                    e.getExposureTime(),
                    this::removeSelf);
        } catch (RadiationDataNotFoundException | InputNotSupportedException e) {
            processException(requireContext(), e.getMessage(), null, null, this::removeSelf);
        } catch (InvalidRadiationDataTypeException e) {
            processException(requireContext(),
                    e.getMessage(),
                    R.string.exception_invalid_radiation_data_type_additional,
                    e.getInvalidType(),
                    this::removeSelf);
        }
    }
}
