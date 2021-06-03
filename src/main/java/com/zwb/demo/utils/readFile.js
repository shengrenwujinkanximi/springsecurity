tasks.readFileEDI = function ($, taskContext, args) {
    var file = new java.io.File("");
    /**
     * 存放解析的数据集合
     */
    var readFileValueList = common.arrayList();
    if (file.isDirectory()) {
        var files = file.listFiles();
        for (var readFileIndex in files) {
            var readFile = files[readFileIndex];
            var inputStream = null;
            var inputStreamReader = null;
            var bufferedReader = null;
            try {
                inputStream = new java.io.FileInputStream(readFile);
                inputStreamReader = new java.io.InputStreamReader(inputStream);
                bufferedReader = new java.io.BufferedReader(inputStreamReader);
                var len = "";
                var returnMap = common.hashMap();
                while ((len = bufferedReader.readLine()) != null) {
                    var contents = bufferedReader.readLine();

                    /**
                     * 解析数据
                     */
                    returnMap.putAll(readLine(contents));
                }
                readFileValueList.add(returnMap)
            } catch (e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (e) {

                }
            }
            readFile.delete();
        }
    }
}

readLine = function (value) {
    var map = common.hashMap();
    if (value) {
        if (value.indexOf("EDI_DC40") > -1) {
            /**
             * 计划任务抬头
             */
            if (value.length > 13) {
                /**
                 * 截取的是client
                 */
                map.put("client", value.substring(11, 13));
            }
            if (value.length > 158) {
                map.put("发送端口", value.substring(149, 158));
            }
        }
    }
    return map;
}