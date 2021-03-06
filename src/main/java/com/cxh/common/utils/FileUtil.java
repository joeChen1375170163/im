package com.cxh.common.utils;

import java.io.File;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

public class FileUtil
{
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private volatile static String jarRootpath;

	/**
	 * 获取jar包所在路径
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String getJarRootPath()
	{
		try
		{
			if(StringUtil.isEmpty(jarRootpath))
			{
				String path = ResourceUtils.getURL("classpath:").getPath();
				//=> file:/root/tmp/demo-springboot-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/
				logger.debug("ResourceUtils.getURL(\"classpath:\").getPath() -> " + path);
				//创建File时会自动处理前缀和jar包路径问题  => /root/tmp
				File rootFile = new File(path);
				if (!rootFile.exists())
				{
					logger.info("根目录不存在, 重新创建");
					rootFile = new File("");
					logger.info("重新创建的根目录: " + rootFile.getAbsolutePath());
				}
				logger.debug("项目根目录: " + rootFile.getAbsolutePath());        //获取的字符串末尾没有分隔符 /
				
				jarRootpath = rootFile.getAbsolutePath();
			}
		}
		catch (Exception e)
		{
			logger.info("重新创建的根目录出现异常");
			jarRootpath = "";
		}
		
		return jarRootpath;
	}
}
