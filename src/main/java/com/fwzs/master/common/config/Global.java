/**
 * 
 */
package com.fwzs.master.common.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;

import com.ckfinder.connector.ServletContextFactory;
import com.fwzs.master.common.utils.PropertiesLoader;
import com.fwzs.master.common.utils.StringUtils;
import com.google.common.collect.Maps;

/**
 * 全局配置类
 * 
 * @author numberone
 * @version 2014-06-25
 */
public class Global {

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();

	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader(
			"fwzs.properties");

	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";

	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	/**
	 * 生产任务的状态常量
	 */
	// 草稿
	public final static String DRAFT = "1";
	// 确认
	public final static String CONFIRMATION = "2";
	// 审核(未生产)
	public final static String AUDITING = "3";
	// 生产中
	public final static String MANUFACTURING = "4";
	// 完工
	public final static String COMPLETED = "5";
	// 质检通过
	public final static String QUALITY_CONTROL_PASS = "6";
    // 质检未通过
    public final static String QUALITY_CONTROL_NOT_PASS = "7";
	//数据库字典单位标示
	public static final  String PROD_UNIT="prod_unit";
    /**
     * 出入库的状态常量
     */
    // 未出库
    public final static String BOUNDING = "0";
    // 已出库
    public final static String SHIPPING = "1";
    // 已收货
    public final static String RECEIVED = "2";

    /**
     * 出库错误消息定义
     */
    public final static String BOUND_SUCCESS = "0";
    public final static String BOUND_OUTOFSTOCK = "1";
    public final static String BOUND_INVALIDPACKAGERATE = "2";
    public final static String BOUND_STOCKNOTEXIST = "3";
    public final static String BOUND_PLANNOTEXIST = "4";
    public final static String BOUND_INVALID_PERSON = "5";
    public final static String BOUND_INVALID_NUMBER = "6";
    public final static String BOUND_INVALID_BOXCODE_TYPE = "7";
    public final static String BOUND_INVALID_SCANDATE = "8";
    public final static String BOUND_INVALID_BOXCODES = "9";
    public final static String BOUND_DUPLICATE_BOXCODES = "10";
    public final static String BOUND_BOXCODES_EMPTY = "11";
    public final static String INBOUND_SUCCESS = "12";

    public final static Map<String, String> BOUND_STATUS_MSG_MAP = new HashMap<String, String>() {
        {
            put(INBOUND_SUCCESS, "入库成功");
            put(BOUND_SUCCESS, "出库成功");
            put(BOUND_OUTOFSTOCK, "库存不足");
            put(BOUND_INVALIDPACKAGERATE, "无效的包装比例");
            put(BOUND_STOCKNOTEXIST, "库存不存在");
            put(BOUND_PLANNOTEXIST, "计划不存在");
            put(BOUND_INVALID_PERSON, "操作员为空");
            put(BOUND_INVALID_NUMBER, "无效的数量");
            put(BOUND_INVALID_BOXCODE_TYPE, "无效的箱码类型");
            put(BOUND_INVALID_SCANDATE, "PDA扫描日期不正确");
            put(BOUND_INVALID_BOXCODES, "无效的箱码");
            put(BOUND_DUPLICATE_BOXCODES, "重复出库的箱码");
            put(BOUND_BOXCODES_EMPTY, "箱码列表为空");
        }
    };

    public final static String LOGIN_FLAG_ENABLE = "1";
    public final static String LOGIN_FLAG_DISABLE = "0";

    /**
     * 行政区域级别
     */
    public final static String AREA_TYPE_COUNTRY = "1";
    public final static String AREA_TYPE_PROVINCE = "2";
    public final static String AREA_TYPE_CITY = "3";
    public final static String AREA_TYPE_TOWN = "4";

    /**
     * 码类型
     */
    public final static String CODE_TYPE_SKU = "1";
    public final static String CODE_TYPE_BOX = "2";
    public final static String CODE_TYPE_BIGBOX = "3";

    /**
	 * 上传文件基础虚拟路径
	 */
	public static final String USERFILES_BASE_URL = "/userfiles/";

	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}

	/**
	 * 获取配置
	 * 
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null) {
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}

	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}

	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}

	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}

	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}

	/**
	 * 在修改系统用户和角色时是否同步到Activiti
	 */
	public static Boolean isSynActivitiIndetity() {
		String dm = getConfig("activiti.isSynActivitiIndetity");
		return "true".equals(dm) || "1".equals(dm);
	}

	/**
	 * 页面获取常量
	 * 
	 * @see ${fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	/**
	 * 获取上传文件的根目录
	 * 
	 * @return
	 */
	public static String getUserfilesBaseDir() {
		String dir = getConfig("userfiles.basedir");
		if (StringUtils.isBlank(dir)) {
			try {
				dir = ServletContextFactory.getServletContext()
						.getRealPath("/");
			} catch (Exception e) {
				return "";
			}
		}
		if (!dir.endsWith("/")) {
			dir += "/";
		}
		return dir;
	}

	/**
	 * 获取工程路径
	 * 
	 * @return
	 */
	public static String getProjectPath() {
		// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)) {
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null) {
				while (true) {
					File f = new File(file.getPath() + File.separator + "src"
							+ File.separator + "main");
					if (f == null || f.exists()) {
						break;
					}
					if (file.getParentFile() != null) {
						file = file.getParentFile();
					} else {
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
	}

    public final static String OFFICE_TYPE_COMPANY = "1";

    public final static String USER_TYPE_DEALER = "d";

	//定义计划管理各个菜单的名称，以便返回时跳转到指定的界面
	public final static String PLANMANAGEMENT = "planManagement";

    /**
     * 定义日期格式化格式
     */
    public class DateFormate {
        public final static String PATTERN_CHINATIMEFORMATE = "yyyy-MM-dd HH:mm:ss";
    }

    /**
     * 定义手机防伪追溯模板相关常量
     */
    public class RetrospectTemplateSetting {
        public final static String PROPERTY_FILE_NAME = "retrospectTemplate.properties";
        public final static String RETROSPECT_TEMPLATE_PREFIX = "retrospect.template.";
        public final static String RETROSPECT_TEMPLATE_DEFAULT_KEY = "retrospect.template.default";

        public final static String DEFAULT_TEMPLATE = "classic";
    }
}
