package com.oscarvj.pruebanach.ui.view.photo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oscarvj.pruebanach.databinding.FragmentPhotoBinding
import com.oscarvj.pruebanach.ui.view.main.MainActivity
import com.oscarvj.pruebanach.utils.CameraUtil
import com.oscarvj.pruebanach.utils.LPermissions
import android.graphics.BitmapFactory
import androidx.core.net.toUri
import com.oscarvj.pruebanach.data.firebase.storage.FireBaseStorage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhotoFragment: Fragment(), MainActivity.OnCallPermission {

    private lateinit var binding: FragmentPhotoBinding

    private lateinit var lPermissions: LPermissions

    @Inject
    lateinit var fireBaseStorage: FireBaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lPermissions = LPermissions(requireActivity())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPhotoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity?)!!.setActivityListener(this)

        binding.button.setOnClickListener {
            if(lPermissions.camera()) {
                CameraUtil.dispatchTakeCamera(this)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            lateinit var path: String
            if (resultCode == Activity.RESULT_OK) {
                when (requestCode) {
                    CameraUtil.REQUEST_IMAGE_CAPTURE -> {
                        path = CameraUtil.photoFile.absolutePath
                        val myBitmap = BitmapFactory.decodeFile(path)
                        binding.imageView2.setImageBitmap(myBitmap)
                        fireBaseStorage.uploadImage(CameraUtil.photoFile.name, CameraUtil.photoFile.toUri(), requireContext())
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun permissionSuccess(boolean: Boolean) {
        if (boolean) {
            CameraUtil.dispatchTakeCamera(this)
        }
    }
}