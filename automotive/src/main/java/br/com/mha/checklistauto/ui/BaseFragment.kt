package br.com.mha.checklistauto.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.mha.checklistauto.sensors.VoiceSensor
import org.koin.android.ext.android.inject

abstract class BaseFragment: Fragment() {
    protected val voiceSensor: VoiceSensor by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setAudioSystem()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun setAudioSystem() {
        if (audioPermissionsAreNotGranted()) {
            askPermissionToTheUser()
        } else {
            startSpeechRecognizer()
        }
    }

    private fun askPermissionToTheUser() {
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) startSpeechRecognizer()
        }.launch(Manifest.permission.RECORD_AUDIO)
    }

    abstract fun startSpeechRecognizer()

    protected fun audioPermissionsAreNotGranted() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.RECORD_AUDIO
    ) != PackageManager.PERMISSION_GRANTED
}