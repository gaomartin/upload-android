一、概述
此文档旨在指导Android应用开发者，如何在应用中植入media_upload_sdk (以下简称SDK)，为开发者提供简单、方便的接口， 帮助开发者实现Android平台视频上传功能。
SDK提供了多层面的调用方式，开发者可根据自己的需求定制化开发上传界面，也可使用默认界面实现快速开发。
SDK基于jar包及so动态库集成方案，使用者需引入media_upload_demo工程中的libs/media_upload_sdk.jar。该jar包是已经混淆过的，在加入第三方应用后请不要再混淆。
media_upload_demo是一个Android示例工程，可以帮助开发者了解如何使用SDK相关功能和接口使用。
二、阅读对象
本文档面向所有使用该SDK的开发人员、测试人员、合作伙伴以及对此感兴趣的其他人员。
三、SDK 功能说明
SDK以开发者为中心，以快捷创建媒体播放为目标，具有以下特性：
1. 支持mp4、3pg等视频格式资源
2. 支持本地视频上传、录像上传
四、使用说明
4.1 环境准备
1 从 Eclipse 导入Android示例工程，里面包含的示例窗体：主窗体MainActivity
2 检查工程中 AndroidManifest.xml 的必要权限：
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />

4.2 初始化
1 在主窗体包含两个点击按钮，相册和录像，分别为从本地相册或文件管理中选择视频文件和直接录像并上传。
2 在播放器窗体的onCreate函数内添加上传视频的初始化代码：
mUploadManager = UploadManager.init(mContext);
mUploadManager.setListener(this);
其中mUploadManager是上传管理类， 通过init(mContext) 实现初始化。
其中setListener是视频上传的监听接口UpLoadManagerListener的对象，细节见接口定义。

4.3 上传控制
1 添加上传任务
UploadManager.insertVideo(String videoPath, String name)

2 开始上传：
UploadManager.uploadFile (UploadInfo info)
3 更新上传任务：
UploadManager.updateUpload (UploadInfo info)
4 从数据库搜索所有上传任务：
UploadManager.searchAllUploads ( )
5 通过任务ID删除此任务：
UploadManager.deleteUploadById(long id)
其中position单位是秒。
6 查询所有视频路径：
UploadManager. searchVideoPaths(String username)

五、接口定义
以下是主要的接口定义及说明，完整的定义以实际代码为准，如有疑问可随时反馈。
1 上传控制接口定义（见PPTVSdkMgr.java）：
/**
    * @brief 初始化
    * @param[in] context 窗体上下文
    * @return 无
    * @throws Exception
    */
public static UploadManager init(Context context)

/**
    * @brief 添加上传任务
    * @param[String] videoPath视频路径
* @param[String] name视频名称
    * @return 无
    */
public void insertVideo(String videoPath, String name)

/**
    * @brief 开始上传
    * @param[UploadInfo] info上传实体类
    * @return 无
    * @throws Exception
    */
public void uploadFile(final UploadInfo info)

/**
    * @brief 更新上传
    * @param[UploadInfo] info上传实体类
    * @return 无
    * @throws Exception
    */
public void updateUpload(UploadInfo info)

/**
    * @brief 搜索所有任务
    * @return List<UploadInfo> 任务List
    * @throws Exception
    */
public List<UploadInfo> searchAllUploads()

/**
    * @brief 搜索所以视频路径
    * @return List<String> 路径List
    * @throws Exception
    */
public List<String> searchVideoPaths()

/**
    * @brief 通过Id删除任务
    * @return int id  任务Id
    * @throws Exception
    */
public void deleteUploadById(int id)
   

2 上传监听接口定义（见UpLoadManagerListener.java）：
 
public interface UpLoadManagerListener {
   /**
     * @brief 上传状态更新
     * @param[UploadInfo] info 任务实体
     */

   public void onStateChange(UploadInfo info);
/**
     * @brief 上传错误
     * @param[UploadInfo] info 任务实体
     */

   public void onUploadError(UploadInfo info);

/**
     * @brief 上传成功
     * @param[UploadInfo] info 任务实体
     */

   public void onUploadSuccess(UploadInfo info);

/**
     * @brief 添加任务成功
     * @param[boolean] isAddSuccess
     */

   public void onAddUploadTask(boolean isAddSucces);
}
