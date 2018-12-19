package com.aaron.demo.common.store

import android.util.Log
import com.yueren.util.DiskStorageUtils
import com.yueren.util.FileUtils
import java.io.File

/**
 * Created on 15/9/11.
 *
 * @author aaron.huang
 * @version 1.0.0
 */
object DiskStorageManager {

    val TAG = "DiskStorageManager"
    private val PATH_STORAGE_IMAGE_CACHE = "/image/"
    private val PATH_STORAGE_FILE = "/file/"
    private val PATH_STORAGE_FILE_APK = "apk/"
    private val PATH_STORAGE_LOG = "log/"
    private val PATH_STORAGE_RESOURCES = "resources/"
    private val PATH_STORAGE_RESOURCES_NOMEDIA = "resources/.nomedia"

    var appPath: String? = null
        private set //应用目录
    var imagePath: String? = null
        private set //图片缓存目录
    var filePath: String? = null
        private set//file的文件总目录
    var apkFileStoragePath: String? = null
        private set//apk下载目录
    var mLogFileStoragePath: String? = null
        private set//log目录
    var resourcesPath: String? = null
        private set//resource目录
    var resourcesFile: String? = null
        private set//resource目录下.nomedia文件
    /**
     * @return 返回应用存储根目录 sdcard存在的情况下返回sdcard目录，如果不存在返回手机本地data目录
     */
    private val deviceRootPath: String
        get() {
            var path = DiskStorageUtils.sdcardDirectoryPath
            if (path == null) {
                path = DiskStorageUtils.internalDataStoragePath
            }
            Log.i(TAG, "device root path: " + path)
            return path
        }

    /**
     * 磁盘存储初始化操作，主要是用来创建文件目录
     */
    fun init(folderName: String?) {
        appPath = deviceRootPath + File.separator + folderName
        imagePath = appPath!! + PATH_STORAGE_IMAGE_CACHE
        filePath = appPath!! + PATH_STORAGE_FILE
        apkFileStoragePath = filePath!! + PATH_STORAGE_FILE_APK
        mLogFileStoragePath = filePath!! + PATH_STORAGE_LOG
        resourcesPath = filePath!! + PATH_STORAGE_RESOURCES
        resourcesFile = filePath!! + PATH_STORAGE_RESOURCES_NOMEDIA
        createAppFolder(appPath!!)
        createAppFolder(imagePath!!)
        createAppFolder(filePath!!)
        createAppFolder(apkFileStoragePath!!)
        createAppFolder(mLogFileStoragePath!!)
        createAppFolder(resourcesPath!!)
        createFile(resourcesFile!!)
    }

    /**
     * 创建目录
     *
     * @param path 目录路径
     */
    private fun createAppFolder(path: String) {
        val file = FileUtils.createFolder(path)
        if (file != null) {
            Log.i(TAG, "create root path success: " + file.path)
        } else {
            Log.i(TAG, "create root path fail")
        }
    }

    /**
     * 创建隐藏音频、图片的.nomedia文件
     *
     * @param path     .nomedia文件路径
     */
    private fun createFile(path: String) {
        val file = FileUtils.createFile(path)
        if (file != null) {
            Log.i(TAG, "create root file success: " + file.path)
        } else {
            Log.i(TAG, "create root file fail")
        }
    }

}
