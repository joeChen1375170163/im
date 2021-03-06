package com.cxh.im.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cxh.common.ResponseResult;
import com.cxh.common.utils.FileUtil;
import com.cxh.common.utils.StringUtil;
import com.cxh.im.mq.entity.Customer;
import com.cxh.im.websocket.IMManage;

@Controller
@RequestMapping("/file")
public class ImAction
{

    private static final Logger logger = LoggerFactory.getLogger(ImAction.class);

    /**
     * 上传文件
     * 
     * @param file
     *            发送的文件
     * @param id
     *            发送者的文件标识，如客服发送：id=agent_ + 客服id
     * @return 生成的文件id和文件名称
     */
    @ResponseBody
    @RequestMapping(value = "/importFile")
    public ResponseResult importFile(MultipartFile file, String id)
    {
        try
        {
            if (!StringUtil.isEmpty(FileUtil.getJarRootPath()))
            {
                String fileId = id;
                String fileName = file.getOriginalFilename();

                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String dateStr = format.format(date);
                fileId += "_" + dateStr;
                // 得到上传的存放文件路径
                String path = FileUtil.getJarRootPath() + File.separatorChar + "static" + File.separatorChar
                        + "fileLocal";
                File upload = new File(path);
                if (!upload.exists())
                {
                    upload.mkdirs();
                }
                // 根据当前系统获得分隔符：
                String filePath = path + File.separatorChar + fileId + "_" + fileName;
                file.transferTo(new File(filePath)); // 保存文件

                Map<String, String> map = new HashMap<String, String>();
                map.put("fileId", fileId);
                map.put("fileName", fileName);
                logger.info("importFile() success, 文件上传成功,filePath:" + filePath);
                return ResponseResult.success(map);
            }
            else
            {
                logger.error("importFile() error, 创建文件目录失败");
                return ResponseResult.error("文件上传失败");
            }

        }
        catch (Exception e)
        {
            logger.error("importFile() error, 文件上传失败", e);
            return ResponseResult.error("文件上传失败");
        }

    }

    /**
     * 下载文件
     * 
     * @param request
     * @param response
     * @param id
     *            文件id
     * @param fileName
     *            文件名称
     */
    @RequestMapping(value = "/loadOutFile/{fileId}/{fileName}")
    // public void loadOutFile(HttpServletRequest request, HttpServletResponse
    // response, String fileId, String fileName)
    public void loadOutFile(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileId,
            @PathVariable String fileName)
    {
        FileInputStream fileIn = null;
        OutputStream fileOut = null;
        BufferedInputStream bis = null;
        try
        {
            if (!StringUtil.isEmpty(FileUtil.getJarRootPath()))
            {
                fileName = URLDecoder.decode(fileName, "UTF-8");
                // 解决IE下载中文乱码
                String userAgent = request.getHeader("user-agent").toLowerCase();
                String loadFileName = fileName;
                // userAgent.contains("like gecko")
                if (userAgent.contains("msie"))
                {
                    // win10 ie edge 浏览器 和其他ie内核的浏览器
                    loadFileName = URLEncoder.encode(loadFileName, "UTF-8").replace("+", " ");
                }
                else
                {
                    // 其他浏览器 ：Firefox、 Google
                    loadFileName = new String(loadFileName.getBytes("UTF-8"), "ISO-8859-1");
                }
                response.setContentType("application/octet-stream;charset=ISO-8859-1");
                response.setBufferSize(4096);
                response.addHeader("Content-Disposition", "attachment;filename = \"" + loadFileName + "\"");
                String webRoot = FileUtil.getJarRootPath() + File.separatorChar + "static" + File.separatorChar
                        + "fileLocal";

                byte[] buffer = new byte[1024];

                String fosPath = webRoot + File.separator + fileId + "_" + fileName;
                fileIn = new FileInputStream(fosPath);
                bis = new BufferedInputStream(fileIn);
                fileOut = response.getOutputStream();
                // 写文件
                int i = bis.read(buffer);
                while (i != -1)
                {
                    fileOut.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                logger.info("loadOutFile() success: 文件下载成功");
            }
            else
            {
                logger.error("loadOutFile() error: 获取下载文件的存放目录失败");
            }

        }
        catch (Exception e)
        {
            logger.error("loadOutFile() error: 文件下载失败", e);
        }
        finally
        {
            if (null != fileOut)
            {
                try
                {
                    fileOut.close();
                }
                catch (IOException e)
                {
                    logger.error("loadOutFile() 关闭IO流异常：", e);
                }
            }

            if (null != bis)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    logger.error("loadOutFile() 关闭IO流异常：", e);
                }
            }

            if (null != fileIn)
            {
                try
                {
                    fileIn.close();
                }
                catch (IOException e)
                {
                    logger.error("loadOutFile() 导出文件异常：", e);
                }
            }
        }
    }

    /**
     * 上传图片
     * 
     * @param file
     *            发送的图片
     * @param id
     *            发送者的文件标识，如客服发送：id=agent_ + 客服id
     * @return 图片id和图片存放路径
     */
    @ResponseBody
    @RequestMapping(value = "/importImg")
    public ResponseResult importImg(MultipartFile file, String id)
    {
        try
        {
            if (!StringUtil.isEmpty(FileUtil.getJarRootPath()))
            {
                Map<String, String> map = new HashMap<String, String>();

                // 获取图片存放路径
                String imgId = id;
                String imgName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
                        file.getOriginalFilename().length());

                // 得到上传的存放文件路径
                String webRoot = FileUtil.getJarRootPath() + File.separatorChar + "static" + File.separatorChar
                        + "imgLocal";
                File upload = new File(webRoot);
                if (!upload.exists())
                {
                    upload.mkdirs();
                }

                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String dateStr = format.format(date);
                // 生成imgId来唯一标识一张图片
                imgId = this.getImgIdByClientID(imgId, dateStr);

                String filePath = webRoot + File.separator + imgId + imgName;
                file.transferTo(new File(filePath)); // 保存文件

                map.put("url", "../static/imgLocal/" + imgId + imgName);
                map.put("imgId", imgId);

                logger.info("importImg() success: 上传图片成功！");

                return ResponseResult.success(map);
            }
            else
            {
                logger.error("importImg() error: 获取上传图片存放目录失败");
                return ResponseResult.error("上传图片失败！");
            }
        }
        catch (Exception e)
        {
            logger.error("importImg() error: 上传图片失败", e);
            return ResponseResult.error("上传图片失败！");
        }
    }

    /**
     * 生成图片id
     * 
     * @param agentId
     *            agentId
     * @param appId
     *            appId
     * @param dateStr
     *            上传图片的当前系统时间
     * @return
     */
    public String getImgIdByClientID(String clientId, String dateStr)
    {
        StringBuffer imgId = new StringBuffer("");
        if (!StringUtil.isEmpty(clientId))
        {
            imgId.append(clientId);
            imgId.append("_");
        }
        imgId.append(dateStr);
        return imgId.toString();
    }

    /**
     * 根据客户id获取前面的等待人数
     * 
     * @param customerId
     *            客户id
     * @param isWait
     *            连接时获取等待人数为true,客服掉线重新等待获取等待人数时为false
     *            为true时，从waitCustoners缓存中获取 为false时从agentDropWait
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/queryWaitNum")
    public ResponseResult queryWaitNum(@RequestParam(name = "id") String customerId, boolean isWait)
    {
        int num = 0;
        if (!isWait)
        {
            List<Entry<String, Customer>> customerSort = IMManage.getCustomerSort(IMManage.getAgentDropWaitMap());
            for (int i = 0; i < customerSort.size(); i++)
            {
                if (customerId.equals(customerSort.get(i).getKey()))
                {
                    break;
                }
                else
                {
                    num++;
                }
            }
        }
        else
        {
            num += IMManage.getAgentDropWaitSize();
            List<Entry<String, Customer>> customerSort = IMManage.getCustomerSort(IMManage.getWaitCustomersMap());
            for (int i = 0; i < customerSort.size(); i++)
            {
                if (customerId.equals(customerSort.get(i).getKey()))
                {
                    break;
                }
                else
                {
                    num++;
                }
            }
        }
        return ResponseResult.success(num);
    }
}
