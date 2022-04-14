package com.ynby.platform.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * 阡陌2.0
 * 代码生成器
 * @author gaogao
 * @date 2019-07-15 17:42.
 */
public class CodeGenerator {


    /* -------------------------------------- 这里进行配置以下 -------------------------------------- */

    //数据库配置
    private static final String DATABASENAME = "operation_center";
    private static final String DATABASE_URL = "192.168.32.39:3306";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "Ynby@2019";

	// 区分是platform管理系统还是portal门户
	private static final String PARENT_MODULE_NAME = "platfrom-center";
    // 模块名称
    private static final String MODULE_KEY = "operation";


    //需要生成的表
    private static final String[] GENERATOR_TABLES = {
            "sms_send_record"
    };

	/**
	 * 需要生成的表模糊匹配，如果GENERATOR_TABLES有数据则该静态常量不生效
	 */
	private static final String LIKE_TABLE = "sys_config%";

    /* -------------------------------------- 以上为需要手动配置部分 -------------------------------------- */

	// 生成器生成代码在该模块下
	private static final String MODULE_NAME = "baiyao-ops/baiyao-develop";

    private static final String MODULE_PATH = String.format("com.ynby.platform.%s", MODULE_KEY);
    // mapper xml 输出路径
    private static final String XML_PATH = String.format("/src/main/java/com/ynby/platform/%s/biz/dao", MODULE_KEY);

    /**
     * 固定字段,生成器在第二级目录时此字段务必设置为空
	 */
	private static final String SUB_MODULE_NAME = "";
    /* -------------------------------------- task开始 -------------------------------------- */

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + "/" + MODULE_NAME;
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("lianghui");
        gc.setActiveRecord(true);
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        // 文件名
//		gc.setMapperName("%sDao");
//		gc.setXmlName("%sMapper");
//		gc.setServiceName("%sService");
//		gc.setServiceImplName("%sServiceImpl");
//		gc.setControllerName("%sController");
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(String.format("jdbc:mysql://%s/%s?useUnicode=true&useSSL=false&characterEncoding=utf8", DATABASE_URL, DATABASENAME));
        // dsc.setSchemaName("public");
		dsc.setDriverName("com.mysql.cj.jdbc.Driver");
		dsc.setUsername(DATABASE_USERNAME);
		dsc.setPassword(DATABASE_PASSWORD);
		mpg.setDataSource(dsc);

		// 包配置
		PackageConfig pc = new PackageConfig();

		pc.setModuleName("");
		pc.setParent(MODULE_PATH);
		pc.setEntity("api.pojo.entity");
		pc.setMapper("biz.dao");
		pc.setService("biz.service");
		pc.setServiceImpl("biz.service.impl");
		pc.setController("biz.controller");

		mpg.setPackageInfo(pc);

		// 自定义配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
        };
//
        // 如果模板引擎是 freemarker
//		String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + XML_PATH + "/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setChainModel(true);
        strategy.setRestControllerStyle(true);
        //这里写表名
		if (GENERATOR_TABLES.length > 0) {
			strategy.setInclude(GENERATOR_TABLES);
		} else {
			strategy.setLikeTable(new LikeTable(LIKE_TABLE));
		}
		strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.execute();
    }

}
