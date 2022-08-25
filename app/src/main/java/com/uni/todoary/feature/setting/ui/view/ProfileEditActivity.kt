package com.uni.todoary.feature.setting.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.uni.todoary.databinding.ActivityProfileEditBinding
import com.uni.todoary.feature.auth.data.dto.User
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.google.android.material.snackbar.Snackbar
import com.uni.todoary.base.ApiResult
import com.uni.todoary.feature.setting.ui.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.io.FileNotFoundException
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import android.content.DialogInterface
import android.provider.Settings
import com.uni.todoary.BuildConfig
import com.uni.todoary.R
import com.uni.todoary.feature.category.ui.view.SettingCalendarBottomSheet

@AndroidEntryPoint
class ProfileEditActivity : AppCompatActivity(){
    lateinit var binding: ActivityProfileEditBinding
    private val userModel : ProfileViewModel by viewModels()
    lateinit var uri : Uri
    val PERMISSIONS_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.profileeditConfirmBtn.setOnClickListener {
            userModel.updateUser(binding.profileeditNameEt.text.toString(), binding.profileeditIntroEt.text.toString())
            userModel.changeProfileImg()
        }

        initClickListeners()
        requestPermission()   // 퍼미션 체크

        // Data Binding
        val userObserver = Observer<User>{ user ->
            binding.profileeditNameEt.setText(user.nickname)
            binding.profileeditIntroEt.setText(user.introduce)
            if (user.profileImgUrl == null || user.profileImgUrl == "https://todoarybucket.s3.ap-northeast-2.amazonaws.com/todoary/users/admin/default_profile_img.jpg"){
//                Glide.with(this)
//                    .load(R.drawable.bg_profile_default)
//                    .into(binding.profileImageIv)
                binding.profileImageIv.setImageResource(R.drawable.bg_profile_default)
            } else {
                Glide.with(this)
                    .load(user.profileImgUrl)
                    .circleCrop()
                    .into(binding.profileImageIv)
            }
        }
        userModel.user.observe(this, userObserver)

        setApiWatchers()
    }

    private fun setApiWatchers(){
        userModel.updateResult.observe(this, {
            when (it.status){
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {
                    Snackbar.make(binding.profileeditConfirmBtn, "프로필 정보 변경에 성공했습니다.", Snackbar.LENGTH_SHORT).show()
                }
                else -> Toast.makeText(this, "code : ${it.code}, message : ${it.message}", Toast.LENGTH_SHORT).show()
            }
        })  // 프로필 정보 변경 API
        userModel.changeImgResult.observe(this, {
            when (it.status){
                ApiResult.Status.LOADING -> {}
                ApiResult.Status.SUCCESS -> {
                    // updateResult에서 스낵바 메시지 띄우고 1.5초 뒤에 finish하도록
                    // 사진 변경되도, 변경 안되도 모두 success로 오기 때문에 finish됨
                    val handler = android.os.Handler(Looper.getMainLooper())
                    handler.postDelayed(Runnable {
                        finish()
                    }, 1500)
                }
                ApiResult.Status.API_ERROR -> {
                    when (it.code){
                        5001, 5002 -> {
                            Toast.makeText(this, "해당하는 이미지 파일의 엑세스 권한이 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this, "Database Error!!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                ApiResult.Status.NETWORK_ERROR -> {}
            }
        })      // 프로필 이미지 변경 API
    }

    // 갤러리 띄우기 위한 launcher 및 콜백
    @SuppressLint("Range")
    var launcher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            uri = result.data!!.data!!
            try {
                // 받아온 이미지 프로필로 저장
                Glide.with(this)
                    .load(uri)
                    .circleCrop()
                    .into(binding.profileImageIv)
//                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
//                binding.profileImageIv.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            //커서 사용해서 경로 확인
            val cursor =
                contentResolver.query(Uri.parse(uri.toString()), null, null, null, null)!!
            cursor.moveToFirst()
            val mediaPath = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            val file = File(mediaPath)
            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("profile-img", file.name, requestFile)
            userModel.setUri(body)
        } else {
            Toast.makeText(this, "사진 업로드 실패", Toast.LENGTH_LONG).show();
        }
    }

    fun deletepic(){
        userModel.deleteProfileImg()
    }

    fun editpic() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        launcher.launch(intent)
    }

    // 외부 저장소에 대한 권한 check
    private fun requestPermission() {
        val shouldProviceRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) //사용자가 이전에 거절한적이 있어도 true 반환
        if (shouldProviceRationale) {
            Log.d("reqreq", "true")
            //앱에 필요한 권한이 없어서 권한 요청
            ActivityCompat.requestPermissions(
                this@ProfileEditActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSIONS_REQUEST_CODE
            )
        } else {
            Log.d("reqreq", "false")
            ActivityCompat.requestPermissions(
                this@ProfileEditActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSIONS_REQUEST_CODE
            )
            //권한있을때.
            //오레오부터 꼭 권한체크내에서 파일 만들어줘야함
//            makeDir()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    //권한 허용 선택시
                    //오레오부터 꼭 권한체크내에서 파일 만들어줘야함
//                    makeDir()
                } else {
                    //사용자가 권한 거절시
                    denialDialog()
                }
                return
            }
        }
    }

    fun denialDialog() {
        AlertDialog.Builder(this)
            .setTitle("알림")
            .setMessage("저장소 권한이 필요합니다. 환경 설정에서 저장소 권한을 허가해주세요.")
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts(
                    "package",
                    BuildConfig.APPLICATION_ID, null
                )
                intent.data = uri
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent) //확인버튼누르면 바로 어플리케이션 권한 설정 창으로 이동하도록
            })
            .create()
            .show()
    }

    private fun initClickListeners(){
        //툴바
        binding.profileEdit.toolbarBackMainTv.text = "계정"
        binding.profileEdit.toolbarBackIv.setOnClickListener {
            finish()
        }

        binding.profileditPiceditTv.setOnClickListener {
            val bottomSheet = EditProfileImgBottomSheet()
            bottomSheet.show(supportFragmentManager,bottomSheet.tag)
        }
    }
    override fun onBackPressed() {
        finish()
    }

    //닉네임, 한줄소개 변경
//    private fun ProfileChange() {
//        val nickname = binding.profileeditNameEt.text.toString()
//        val introduce = binding.profileeditIntroEt.text.toString()
//        val ProfileChangeService = AuthService()
//        ProfileChangeService.setProfileChangeView(this)
//        val request = ProfileChangeRequest(nickname,introduce)
//        ProfileChangeService.ProfileChange(request)
//    }

//    override fun ProfileChangeLoading() {
//    }
//
//    override fun ProfileChangeSuccess() {
//        Log.d("변경","성공")
//
//        val user = getUser()!!
//        user.nickname=binding.profileeditNameEt.text.toString()
//        user.introduce=binding.profileeditIntroEt.text.toString()
//        saveUser(user)
//
//        val intent = Intent(this, ProfileActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        userModel.updateUser(binding.profileeditNameEt.text.toString(), binding.profileeditIntroEt.text.toString())
//
//        startActivity(intent)
//    }
//
//    override fun ProfileChangeFailure(code: Int) {
//        Log.d("변경","실패")
//        Log.d("Error", code.toString())
//    }

}
