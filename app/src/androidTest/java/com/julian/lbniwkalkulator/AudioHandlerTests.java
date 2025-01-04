package com.julian.lbniwkalkulator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.julian.lbniwkalkulator.util.AudioHandler;

@RunWith(AndroidJUnit4.class)
public class AudioHandlerTests {

    private AudioHandler audioHandler;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        audioHandler = new AudioHandler(context);
    }

    @Test
    public void testPlayAudio() {
        audioHandler.play(R.raw.warning_music);
        assertTrue(audioHandler.getMediaPlayer().isPlaying());
    }

    @Test
    public void testStopAudio() {
        audioHandler.play(R.raw.warning_music);
        audioHandler.stop();
        assertThat(audioHandler.getMediaPlayer(), CoreMatchers.nullValue());
    }

    @After
    public void cleanUp() {
        audioHandler.stop();
    }
}