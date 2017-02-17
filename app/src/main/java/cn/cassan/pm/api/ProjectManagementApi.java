package cn.cassan.pm.api;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import cn.cassan.pm.model.MobileContact;
import cn.cassan.pm.util.JSONUtil;

/**
 * 项目管理系统WEB API 接口
 * Created by chenmaotou on 2016/9/17.
 */
public class ProjectManagementApi {

    /**
     * 注册用户
     *
     * @param phone    手机号
     * @param password 密码
     * @param handler  api回调句柄
     */
    public static void register(String phone, String password, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("password", password);
        params.put("code", 12345);
        String url = "app/Employee/register";
        ApiHttpClient.post(url, params, handler);

    }

    /**
     * 登陆到系统
     *
     * @param username 用户名
     * @param password 密码
     * @param handler  回调句柄
     */
    public static void login(String username, String password, String systemVersion, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        params.put("MobileSystem", 1);
        params.put("systemVersion", systemVersion);
        params.put("code", 12345);
        String url = "app/Employee/login";
        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 更新百度channel
     *
     * @param token token
     * @param baiduUserId 百度baiduUserId
     * @param baiduChannelId 百度channelid
     * @param handler  回调句柄
     */
    public static void updateBaiduPush(String token,String baiduUserId, String baiduChannelId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("baiduUserId", baiduUserId);
        params.put("baiduChannelId", baiduChannelId);
        String url = "app/Employee/updateBaiduPush";
        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 注销
     *
     * @param token   令牌
     * @param handler 回调句柄
     */
    public static void logout(String token, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        String url = "app/Employee/logout";
        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 获取指定用户的数据
     *
     * @param token      令牌
     * @param employeeId 员工编号
     * @param handler    回调句柄
     */
    public static void getUserInfo(String token, int employeeId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("employeeid", employeeId);
        String url = "app/Employee/UserInfo";
        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 根据用户编号集合获取用户信息
     *
     * @param userids 用户编号
     * @param handler 回调句柄
     */

    public static void getUserInfoes(String[] userids, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", userids);
        String url = "app/Employee/getUserByIds";
        ApiHttpClient.post(url, params, handler);
    }


    /**
     * 发好友请求
     *
     * @param token      用户token
     * @param employeeId 被请求人id
     * @param handler    回调句柄
     */

    public static void addFriend(String token, int employeeId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("employeeId", employeeId);
        String url = "/app/Employee/AddFriend";
        ApiHttpClient.post(url, params, handler);
    }


    /**
     * 获取我的好友请求
     *
     * @param token   用户token
     * @param handler 回调句柄
     */

    public static void friendRequest(String token, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        String url = "/app/Employee/FriendRequest";
        ApiHttpClient.post(url, params, handler);
    }


    /**
     * 接受好友请求
     *
     * @param token     用户token
     * @param requestId 请求id
     * @param handler   回调句柄
     */

    public static void acceptAddFriend(String token, int requestId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("requestId", requestId);
        String url = "/app/Employee/AcceptAddFriend";
        ApiHttpClient.post(url, params, handler);
    }


    /**
     * 删除好友
     *
     * @param token    用户token
     * @param friendId 好友id
     * @param handler  回调句柄
     */

    public static void deleteFriend(String token, int friendId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("friendId", friendId);
        String url = "/app/Employee/DeleteFriend";
        ApiHttpClient.post(url, params, handler);
    }


    /**
     * 获取好友列表
     *
     * @param token    用户token
     * @param page     页数，0获取全部
     * @param pageSize 每页数据量
     * @param handler  回调句柄
     */

    public static void getFriendsList(String token, int page, int pageSize, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("page", page);
        params.put("pageSize", pageSize);
        String url = "/app/Employee/GetFriends";
        ApiHttpClient.post(url, params, handler);
    }


    /**
     * 获取省份城市列表
     *
     * @param token   用户token
     * @param handler 回调句柄
     */

    public static void getProvinceCityInfo(String token, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        String url = "/app/Region/ProvinceCityInfo";
        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 获取我的项目列表
     *
     * @param token   用户token
     * @param handler 回调句柄
     */

    public static void getProjectList(String token, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        String url = "/app/Project/GetProjectList";
        ApiHttpClient.post(url, params, handler);
    }


    /**
     * @param token
     * @param ProjectId
     * @param NotifyMessageType
     * @param PageSize
     * @param ContentLength
     * @param UpdateStatus
     * @param handler
     */

    public static void GetNewProjectMessageList(String token, int ProjectId, int NotifyMessageType, int PageSize, int ContentLength, boolean UpdateStatus, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("ProjectId", ProjectId);
        params.put("NotifyMessageType", NotifyMessageType);
        params.put("ContentLength", ContentLength);
        params.put("ContentLength", ContentLength);
        params.put("UpdateStatus", UpdateStatus);


        String url = "/app/Project/GetNewProjectMessageList";
        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 获取与项目关联的部门的子部门
     *
     * @param token        用户token
     * @param projectId    项目id
     * @param parentDeptId 子部门id，0为根节点
     * @param handler      回调句柄
     */

    public static void getProjectDepartment(String token, int projectId, int parentDeptId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("projectId", projectId);
        params.put("parentDeptId", parentDeptId);
        String url = "/app/Project/GetProjectDepartment";
        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 创建项目
     *
     * @param token
     * @param Name                     项目名称
     * @param ManagerName              负责人名称
     * @param PlanToBegin              开始时间
     * @param PlanToEnd                结束时间
     * @param CityId                   城市id
     * @param ConstructionCompanyId    公司id
     * @param ProjectMemberIds         项目成员id
     * @param asyncHttpResponseHandler 回调句柄
     */

    public static void createProject(String token, String Name, String ManagerName, String PlanToBegin, String PlanToEnd, int ProvinceId, int CityId, int ConstructionCompanyId, String ProjectMemberIds, AsyncHttpResponseHandler asyncHttpResponseHandler) {

        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("Name", Name);
        params.put("ManagerName", ManagerName);
        params.put("PlanToBegin", PlanToBegin);
        params.put("PlanToEnd", PlanToEnd);
        params.put("ProvinceId", ProvinceId);
        params.put("CityId", CityId);
        params.put("ConstructionCompanyId", ConstructionCompanyId);
        params.put("ProjectMemberIds", ProjectMemberIds);
        String url = "/app/Project/CreateProjectString";
        ApiHttpClient.post(url, params, asyncHttpResponseHandler);
    }

    /**
     * 获取与项目关联的部门的员工
     *
     * @param token        用户token
     * @param projectId    项目id
     * @param parentDeptId 子部门id，0为根节点
     * @param handler      回调句柄
     */

    public static void getProjectDepartmentEmployee(String token, int projectId, int parentDeptId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("projectId", projectId);
        params.put("parentDeptId", parentDeptId);
        String url = "/app/Project/GetProjectDepartmentEmployee";
        ApiHttpClient.post(url, params, handler);
    }


    /**
     * 删除项目
     *
     * @param token        用户token
     * @param projectId    项目id
     */

    public static void deleteProject(String token, int projectId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("projectId", projectId);
        String url = "/app/Project/DeleteProject";
        ApiHttpClient.post(url, params, handler);
    }


    /** 
     * 结束项目
     *
     * @param token        用户token
     * @param projectId    项目id
     */

    public static void closeProject(String token, int projectId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("projectId", projectId);
        String url = "/app/Project/closeProject";
        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 向项目中添加人员
     *
     * @param token       用户token
     * @param projectId   项目id
     * @param employeeIds 拉的人的id
     * @param handler     回调句柄
     */

    public static void addProjectEmployee(String token, int projectId, int[] employeeIds, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("projectId", projectId);
        params.put("employeeIds", employeeIds);
        String url = "/app/Project/AddProjectEmployee";
        ApiHttpClient.post(url, params, handler);
    }

    /**
     * @param token     用户token
     * @param projectId 项目id
     * @param handler   回调句柄
     */
    public static void getProjectInfo(String token, int projectId, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("projectId", projectId);
        String url = "/app/Project/GetProjectInfo";
        ApiHttpClient.post(url, params, handler);
    }

    public static void updateProjectInfo(String token, int projectId, int ProjectId, String ProjectName, String ManagerName, String PlanToBegin, String PlanToEnd, int ProvinceId, int CityId, int ConstructionCompanyId, AsyncHttpResponseHandler handler) {

        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("projectId", projectId);
        params.put("ProjectName", ProjectName);
        params.put("ManagerName", ManagerName);
        params.put("PlanToBegin", PlanToBegin);
        params.put("PlanToEnd", PlanToEnd);
        params.put("projectId", projectId);
        params.put("CityId", CityId);
        params.put("ConstructionCompanyId", ConstructionCompanyId);
        String url = "/app/Project/UpdateProject";
        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 获取群组列表
     *
     * @param token
     * @param handler
     */
    public static void getChatGroupList(String token, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        String url = "/app/Chat/ChatGroupList";
        ApiHttpClient.post(url, params, handler);
    }

    public static void getChatProjectGroupList(String token, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        String url = "/app/Chat/ProjectChatGroupList";
        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 获取手机里的所有app用户的列表
     *
     * @param token        用户token
     * @param mobileString 对象Array的json字符串，对象属性为：MobilePhone（电话） Name（姓名）
     */

    public static void getMobileAppUser(String token, String mobileString, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("mobileString", mobileString);
        String url = "/app/Employee/getMobileAppUser";
        ApiHttpClient.post(url, params, handler);
    }

    /**
     * @param token
     * @param contacts
     * @param handler
     */
    public static void getMobileAppUser(String token, List<MobileContact> contacts, AsyncHttpResponseHandler handler) {

        if (contacts == null)
            return;
        String mobileString = JSONUtil.serializeObject(contacts);
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("mobileString", mobileString);
        String url = "/app/Employee/getMobileAppUser";
        ApiHttpClient.post(url, params, handler);
    }

    /**
     * 获取项目的资料
     *
     * @param token
     * @param DocumentType
     * @param ProjectId
     * @param Page
     * @param PageSize
     * @param handler
     */
    public static void getDocumentByType(String token, int DocumentType, int ProjectId, int Page, int PageSize, AsyncHttpResponseHandler handler) {

        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("DocumentType", DocumentType);
        params.put("ProjectId", ProjectId);
        params.put("token", token);
        params.put("Page", Page);
        params.put("PageSize", PageSize);
        String url = "/app/Document/GetDocumentByType";
        ApiHttpClient.post(url, params, handler);
    }
}
