package com.example.ihramconnect.view.home

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ihramconnect.R
import com.example.ihramconnect.data.ApiConfig
import com.example.ihramconnect.data.ApiService
import com.example.ihramconnect.data.Recording
import com.example.ihramconnect.view.catalog.RecordingsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException

class UstadActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null
    private lateinit var recordingsRecyclerView: RecyclerView
    private lateinit var recordingsAdapter: RecordingsAdapter
    private var recordings: MutableList<Recording> = mutableListOf()

    private lateinit var recordButton: Button
    private lateinit var stopButton: Button
    private lateinit var uploadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ustad)

        apiService = ApiConfig.apiService

        recordButton = findViewById(R.id.recordButton)
        stopButton = findViewById(R.id.stopButton)
        uploadButton = findViewById(R.id.uploadButton)
        recordingsRecyclerView = findViewById(R.id.recordingsRecyclerView)
        recordingsRecyclerView.layoutManager = LinearLayoutManager(this)

        recordingsAdapter = RecordingsAdapter(recordings) { recording ->
           deleteRecording(recording)
        }
        recordingsRecyclerView.adapter = recordingsAdapter

        recordButton.setOnClickListener {
            if (checkPermissions()) {
                startRecording()
            }
        }

        stopButton.setOnClickListener {
            stopRecording()
        }

        uploadButton.setOnClickListener {
            if (audioFile != null) {
                uploadRecording(audioFile!! , "test title dari android")
            } else {
                Toast.makeText(this, "No recording available", Toast.LENGTH_SHORT).show()
            }
        }

        loadRecordings()
    }

    private fun checkPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_RECORD_AUDIO_PERMISSION)
            return false
        }
        return true
    }

    private fun startRecording() {
        try {
            audioFile = File.createTempFile("recording", ".mp3", cacheDir)
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(audioFile!!.absolutePath)
                prepare()
                start()
            }

            Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show()
            recordButton.isEnabled = false
            stopButton.isEnabled = true
        } catch (e: IOException) {
            Log.e("MediaRecorder", "startRecording: ${e.message}")
            Toast.makeText(this, "Failed to start recording: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            try {
                stop()
            } catch (e: RuntimeException) {
                Log.e("MediaRecorder", "stopRecording: ${e.message}")
            }
            release()
        }
        mediaRecorder = null
        Toast.makeText(this, "Recording stopped", Toast.LENGTH_SHORT).show()
        recordButton.isEnabled = true
        stopButton.isEnabled = false
    }

    private fun uploadRecording(file: File, title: String) {
        val requestBody = RequestBody.create("audio/*".toMediaTypeOrNull(), file)
        val multipartBody = MultipartBody.Part.createFormData("voiceNote", file.name, requestBody)
        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        var token =  sharedPreferences.getString("auth_token", null);
        Log.i("token", "uploadRecording: ${token}")



        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.uploadRecording("Bearer ${token}",title, multipartBody)
                Log.i("Upload Response", "uploadRecording: ${response.message()}")
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@UstadActivity, "Upload successful", Toast.LENGTH_SHORT).show()
                        loadRecordings()
                    } else {
                        Toast.makeText(this@UstadActivity, "Upload failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("Upload Error", "uploadRecording: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UstadActivity, "Upload error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun loadRecordings() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                var token =  sharedPreferences.getString("auth_token", null);
                val recordings = apiService.getRecordings("Bearer ${token}")
                withContext(Dispatchers.Main) {
                    this@UstadActivity.recordings.clear()
                    this@UstadActivity.recordings.addAll(recordings)
                    recordingsAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UstadActivity, "Failed to load recordings: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteRecording(recording: Recording) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                val token = sharedPreferences.getString("auth_token", null)

                if (token != null) {
                    val response = apiService.deleteRecording("Bearer $token", recording.id)
                    Log.i("ustadActivity", "deleteRecording: ${response.code()}");
                    withContext(Dispatchers.Main) {
                        if(response.isSuccessful){
                            recordings.remove(recording)
                            recordingsAdapter.notifyDataSetChanged()
                            Toast.makeText(this@UstadActivity, "Recording deleted", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this@UstadActivity, "Recording deleted", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@UstadActivity, "Authentication token is missing", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UstadActivity, "Failed to delete recording: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onStop() {
        super.onStop()
        mediaRecorder?.release()
        mediaRecorder = null
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
    }
}
