package logwire.web.service;

import logwire.core.resource.bean.ActionProvider;
import logwire.core.resource.bean.GlobalProvider;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class ExportActionTest implements ActionProvider, GlobalProvider {
    public void doExportActionTest(ActionContext $, String queryName, RequestData request, ResponseData response) throws IOException {
        String tmpPath = "./projects/oppo-code/config/excel/temp/" + java.util.UUID.randomUUID() + ".xlsx";
        String templatePath = "./projects/oppo-code/config/excel/template/plDetailDe.xlsx";
        File file = new java.io.File(tmpPath);
        if (!file.exists()) {
            file.createNewFile();
        }
        Map headerMap = new java.util.HashMap();
        headerMap.put("test_head", "1122222");
        List dataLineList1 = new java.util.ArrayList();
        for (int i = 0; i < 10; i++) {
            Map map = new java.util.HashMap();
            map.put("item_package_count", 7360);
            dataLineList1.add(map);
        }
        // EasyExcelFillCommon.FillData(tmpPath, templatePath, "", dataLineList1);
        String[] stringArray = new String[]{};
        EasyExcelFillCommon.main(stringArray);
        EasyExcelFillCommon.FillData(tmpPath, templatePath, "", dataLineList1);
    }
}
