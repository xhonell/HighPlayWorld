package com.xhonell.servlet;

import com.xhonell.common.Write;
import com.xhonell.entity.Player;
import com.xhonell.service.IndexService;
import com.xhonell.utils.FileUtils;
import com.xhonell.utils.MyFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

import static com.xhonell.utils.MD5Utils.md5;

/**
 * <p>Project:test_01 - IndexServlet
 * <p>POWER by xhonell on 2024-11-23 10:08
 * <p>description：
 * <p>idea：
 *
 * @author xhonell
 * @version 1.0
 * @since 1.8
 */
@MultipartConfig
@WebServlet("/servlet/index/*")
public class IndexServlet extends HttpServlet {
    IndexService indexService = new IndexService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        switch (method) {
            case "insertPlayer":
                insertPlayer(req, resp);
                break;
            case "editPlayer":
                editPlayer(req, resp);
                break;
            case "uploadPer":
                uploadPer(req, resp);
                break;
            default:
                break;
        }
    }

    /**
     * 批量导入
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void uploadPer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("fileUploadPer");
        HSSFWorkbook sheets = new HSSFWorkbook(part.getInputStream()); //解析流获取excel
        HSSFSheet sheet = sheets.getSheetAt(0); //获得第一张sheet
        int lastRowNum = sheet.getLastRowNum();//获取最后一行
        List<Player> list = new ArrayList<>();
        for (int i = 1; i <= lastRowNum; i++) {
            HSSFRow row = sheet.getRow(i);
            Player player = new Player();
            HSSFCell name = row.getCell(1);
            player.setPlayer_nickName(name.getStringCellValue());
            HSSFCell phone = row.getCell(2);
            player.setPlayer_phone(Long.parseLong(phone.getStringCellValue()));
            HSSFCell sex = row.getCell(3);
            player.setPlayer_sex(sex.getStringCellValue());
            HSSFCell cell = row.getCell(4);
            player.setPlayer_birthday(cell.getDateCellValue());
            String phoneStr = phone.getStringCellValue();
            String password = md5(phoneStr.substring(phoneStr.length() - 6));
            player.setPlayer_password(password);
            list.add(player);
        }
        boolean b = indexService.batchAdd(list);
        if (b) {
            Write.writeSuccess(resp);
        } else {
            Write.writeFail(resp, "数据已存在");
        }
    }


    private void editPlayer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("hiddenId"));
        String name = req.getParameter("nickName");
        String sex = req.getParameter("sex");
        Long phone = Long.parseLong(req.getParameter("phone"));
        Date birthday = MyFormatUtils.toDate(req.getParameter("birthday"));
        Part part = req.getPart("photo");
        String submittedFileName = part.getSubmittedFileName();
        Object[] obj;
        if (submittedFileName != null && !submittedFileName.trim().isEmpty()) {

            String suffix = FileUtils.getSuffix(submittedFileName);
            String newName = UUID.randomUUID().toString().replace("-", "") + suffix;
            /*获取相对路径*/
            String relativePath = "/img/" + newName;
            /*获取绝对路径*/
            String realPath = req.getServletContext().getRealPath("");
            String absolutePath = realPath + relativePath;
            System.out.println(absolutePath);
            obj = new Object[]{name, sex, phone, birthday, relativePath, id};
            part.write(absolutePath);

        } else {
            obj = new Object[]{name, sex, phone, birthday, id};
        }


        boolean b = indexService.editPlayer(obj);
        if (b) {
            Write.writeSuccess(resp);
        } else {
            Write.writeFail(resp);
        }
    }

    private void insertPlayer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("nickName");
        String password = req.getParameter("password");
        password = md5(password);
        String sex = req.getParameter("sex");
        Long phone = Long.parseLong(req.getParameter("phone"));
        Date birthday = MyFormatUtils.toDate(req.getParameter("birthday"));
        Part part = req.getPart("photo");
        /*获取文件名称和路径*/
        String submittedFileName = part.getSubmittedFileName();
        String suffix = FileUtils.getSuffix(submittedFileName);
        String realPath = req.getServletContext().getRealPath("");
        String newName = (UUID.randomUUID().toString().replace("-", "")) + suffix;
        /*相对路径*/
        String relativePath = "/img/" + newName;

        /*绝对路径*/
        String absolutePath = realPath + relativePath;
        System.out.println(absolutePath);
        part.write(absolutePath);
        Object[] obj = {name, password, sex, phone, birthday, relativePath};
        boolean b = indexService.insertPlayer(obj);
        if (b) {
            Write.writeSuccess(resp);
        } else {
            Write.writeFail(resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        switch (method) {
            case "selectAll":
                selectAll(req, resp);
                break;
            case "selectById":
                selectById(req, resp);
                break;
            case "deleteById":
                deleteById(req, resp);
                break;
            case "downloadPer":
                downloadPer(req, resp);
                break;
            default:
                break;
        }
    }

    /**
     * 下载excel
     *
     * @param req
     * @param resp
     */
    private void downloadPer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        /*按条件查询*/
        List<Player> players = indexService.selectById(new Object[3]);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet playerSheet = wb.createSheet("player");
        HSSFRow rowTitle = playerSheet.createRow(0);
        rowTitle.createCell(0).setCellValue("玩家编号");
        rowTitle.createCell(1).setCellValue("玩家姓名");
        rowTitle.createCell(2).setCellValue("玩家手机");
        rowTitle.createCell(3).setCellValue("玩家性别");
        rowTitle.createCell(4).setCellValue("玩家生日");

        int rows = 1;
        for (Player player : players) {
            HSSFRow row = playerSheet.createRow(rows);
            row.createCell(0).setCellValue(player.getPlayer_id());
            row.createCell(1).setCellValue(player.getPlayer_nickName());
            row.createCell(2).setCellValue(player.getPlayer_phone());
            row.createCell(3).setCellValue(player.getPlayer_sex());
            row.createCell(4).setCellValue(MyFormatUtils.toString(player.getPlayer_birthday()));
            rows++;
        }
        String fileName = System.currentTimeMillis() + ".xls";
        resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        resp.setContentType("application/vnd.ms-excel");
        wb.write(resp.getOutputStream());
    }

    private void deleteById(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = Integer.parseInt(req.getParameter("id"));
        boolean b = indexService.deleteById(id);
        if (b) {
            Write.writeSuccess(resp);
        } else {
            Write.writeFail(resp);
        }
    }

    private void selectById(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = null;
        if (req.getParameter("id") != null && !req.getParameter("id").trim().isEmpty()) {
            id = Integer.parseInt(req.getParameter("id"));
        }
        String name = req.getParameter("nickName");
        String phone = req.getParameter("phone");
        Object[] obj = {id, name, phone};
        List<Player> players = indexService.selectById(obj);
        Write.writeSuccess(resp, players);
    }

    private void selectAll(HttpServletRequest req, HttpServletResponse resp) {
        Object[] obj = {(Integer.parseInt(req.getParameter("pageNumber")) - 1) * 10};
        List<Player> players = indexService.selectAll(obj);
        Write.writeSuccess(resp, players);
    }


}
