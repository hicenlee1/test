package jksj.concurrent.lyra;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.http.util.TextUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static jksj.concurrent.lyra.ValidateUtils.check;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Test {

    private JSONObject getMenuInfo(JSONObject sourc, String ssCode) {
        JSONObject result = new JSONObject();
        if (sourc != null) {
            String privId = sourc.getString("privId");
            String menuName = sourc.getString("resName");
            String menuCode = sourc.getString("resCode");
            String menuDesc = sourc.getString("resComment");
            String orderId = sourc.getString("orderId");
            String hasPriv = sourc.getString("hasPriv");
            String clickUrl = sourc.getString("resExtInfos");
            String status = sourc.getString("status");

            if ("0".equals(status)) {            // 移除隐藏的菜单
                result.put("privId", privId);
                result.put("menuName", menuName);
                result.put("menuCode", menuCode);
                result.put("menuDesc", menuDesc);
                result.put("clickUrl", clickUrl);
                result.put("orderId", orderId);
                result.put("hasPriv", hasPriv);
                result.put("status", status);
                result.put("productCode", ssCode);
                if (sourc.containsKey("subMenus")) {
                    JSONArray jsonArray = sourc.getJSONArray("subMenus");
                    JSONArray resultArray = new JSONArray();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject sub = getMenuInfo(jsonArray.getJSONObject(i), ssCode);
                        if (sub != null) {
                            resultArray.add(sub);
                        }
                    }
                    if (resultArray.size() > 0) {
                        result.put("subMenus", resultArray);
                    }
                }
                return result;
            }
        }
        return null;
    }

    private void change2Map(String parentName, JSONArray jsonArray, HashMap<String, JSONObject> map, String ssCode) {
        if (jsonArray == null) {
            return;
        }

        for(int i = 0; i < jsonArray.size(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            if (obj != null) {
                String name = obj.getString("menuName");
                String key = null;
                if (!StringUtils.isEmpty(parentName)) {
                    key = parentName + "-" + name;
                } else {
                    key = name;
                }
                map.put(key, obj);
                if (obj.containsKey("subMenus")) {
                    JSONArray jsonArray1 = obj.getJSONArray("subMenus");
                    change2Map(key, jsonArray1, map, ssCode);
                }
            }
        }
    }

    private HashMap<String, HashMap<String, JSONObject>> getSctMenu(String ssCode) throws IOException {
        HashMap<String, HashMap<String, JSONObject>> sctHash = new HashMap<>();

        String resString = IOUtils.toString(Test.class.getResourceAsStream("/spring/sct.text"));
        JSONObject resObj = JSONObject.parseObject(resString);
        JSONArray jsonArray = resObj.getJSONArray("value");
        JSONArray subArray = jsonArray.getJSONObject(0).getJSONArray("subMenus");
        JSONArray result = new JSONArray();
        for (int i = 0; i < subArray.size(); i++) {
            JSONObject sub = getMenuInfo(subArray.getJSONObject(i), ssCode);
            if (sub != null) {
                result.add(sub);
            }
        }

        JSONArray siteArray = result;

        for (int i = 0; i < siteArray.size(); i++) {
            JSONObject jsonObject = siteArray.getJSONObject(i);
            String name = jsonObject.getString("menuName");
            JSONArray jsonArray1 = jsonObject.getJSONArray("subMenus");
            HashMap<String, JSONObject> subHashMap = new HashMap<>();
            change2Map(null, jsonArray1, subHashMap, ssCode);
            sctHash.put(name, subHashMap);
        }

        return sctHash;
    }

    private HashMap<String, JSONArray> getSctJson(String ssCode) throws IOException {
        HashMap<String, JSONArray> sctHash = new HashMap<>();

        String resString = IOUtils.toString(Test.class.getResourceAsStream("/spring/sct.text"));
        JSONObject resObj = JSONObject.parseObject(resString);
        JSONArray jsonArray = resObj.getJSONArray("value");
        JSONArray subArray = jsonArray.getJSONObject(0).getJSONArray("subMenus");
        JSONArray result = new JSONArray();
        for (int i = 0; i < subArray.size(); i++) {
            JSONObject sub = getMenuInfo(subArray.getJSONObject(i), ssCode);
            if (sub != null) {
                result.add(sub);
            }
        }

        JSONArray siteArray = result;

        for (int i = 0; i < siteArray.size(); i++) {
            JSONObject jsonObject = siteArray.getJSONObject(i);
            String name = jsonObject.getString("menuName");
            JSONArray jsonArray1 = jsonObject.getJSONArray("subMenus");
            sctHash.put(name, jsonArray1);
        }
        return sctHash;
    }

    public static void main(String[] args) throws IOException {
        Test t = new Test();
        t.updateStatic();
    }


    public void updateStatic() throws IOException {

        List<Long> siteId = new ArrayList<>();
        List<String> error = new ArrayList<>();
        String account = "haiyang1";

        String ssCode = "report";
        String template = IOUtils.toString(Test.class.getResourceAsStream("/spring/template.text"));
        String mapValue = IOUtils.toString(Test.class.getResourceAsStream("/spring/map.text"));
        String param = "{\n" +
                "    \"base\":[\n" +
                "        {\n" +
                "            \"siteName\":\"商业部广告外发sdk\",\n" +
                "            \"siteNickName\":\"商业部广告外发sdk\",\n" +
                "            \"{appname}\":\"com.meizu.advertise.external\"\n" +
                "        }\n" +
                "     ],\n" +
                "    \"params\":{\n" +
                "        \"reliurl\":\"com.meizu.reli\"\n" +
                "    }\n" +
                "}\n";

        JSONObject paramObj = JSON.parseObject(param);
        JSONArray base = null;
        if (paramObj.containsKey("base")) {
            base = paramObj.getJSONArray("base");
        } else {
            System.out.println("参数中必须带有base业务线节点");
        }

        JSONObject params = paramObj.getJSONObject("params");
        JSONObject mapObj = JSON.parseObject(mapValue);
        JSONObject tempJson = JSON.parseObject(template);
        JSONObject topJson = tempJson.getJSONObject("top_menu");
        String topMenuStr = topJson.getString("data");

        // 获取owner
        HashMap<String, HashMap<String, JSONObject>> sctHash = getSctMenu(ssCode);
        HashMap<String, JSONArray> menuJson = getSctJson(ssCode);

        for (int i = 0; i < base.size(); i++) {
            JSONObject jsonObject = base.getJSONObject(i);
            try {
                if (jsonObject.containsKey("siteName")) {
                    String name = jsonObject.getString("siteName");
                    String nickName = null;
                    if (jsonObject.containsKey("siteNickName")) {
                        nickName = jsonObject.getString("siteNickName");
                    }

                    Site site = new Site();
                    if (TextUtils.isBlank(nickName)) {
                        site.setName(name);
                    } else {
                        site.setName(nickName);
                    }

                    site.setOwner("haiyang1");

                    // 解析title
                    if (tempJson.containsKey("title")) {
                        String title = tempJson.getString("title");
                        if (!StringUtils.isEmpty(title)) {
                            site.setTitle(title);
                        } else {
                            site.setName(name);
                        }
                    }

                    // 解析公告
                    if (tempJson.containsKey("notice")) {
                        String notice = tempJson.getString("notice");
                        if (!StringUtils.isEmpty(notice)) {
                            site.setNotice(notice);
                        }
                    }

                    // 解析命名空间
                    if (tempJson.containsKey("namespace")) {
                        String namespaceName = tempJson.getString("namespace");
//                        if (!StringUtils.isEmpty(namespaceName)) {
//                            // 获取命名空间id
//                            Namespace namespace = new Namespace();
//                            namespace.setName(namespaceName);
//                            LyraWebResult<List<Namespace>>  namespaceList = namespaceService.get(namespace);
//                            Long ids = namespaceList.getValue().get(0).getNsId();
//                            site.setNamespace(ids);
//                        } else {
//                            site.setNamespace(1L);
//                        }
                        site.setNamespace(1L);
                    } else {
                        site.setNamespace(1L);
                    }

                    // 设置状态
                    if (tempJson.containsKey("status")) {
                        String staticStr = tempJson.getString("status");
                        if (!StringUtils.isEmpty(staticStr)) {
                            site.setStatus(staticStr);
                        } else {
                            site.setStatus("offline");
                        }
                    } else {
                        site.setStatus("offline");
                    }
                    if (tempJson.containsKey("config")) {
                        JSONObject config = tempJson.getJSONObject("config");
                        if (config != null) {
                            site.setConfig(config.toJSONString());
                        }
                    }

                    // 解析菜单
                    if (tempJson.containsKey("top_menu")) {
                        List<TopMenu> topMenus = JSON.parseArray(topMenuStr, TopMenu.class);
                        List<String> excludeList = new ArrayList<>();

                        for (TopMenu topMenu : topMenus) {
                            String topMenuName = topMenu.getName();
                            if (!check(topMenu.getSub()) && topMenu.getSub().size() > 0) {
                                List<Menu> menuList = topMenu.getSub();
                                List<Menu> remove = new ArrayList<>();
                                for (Menu menu : menuList) {
                                    if (!fitSct(menu, topMenuName, sctHash, mapObj, name, menuJson)) {
                                        synchronized (Test.class) {
                                            remove.add(menu);
                                        }
                                    }
                                }

                                for (Menu menu2 : remove) {
                                    menuList.remove(menu2);
                                }
                            } else {
                                Menu menu = new Menu();
                                menu.setName(topMenuName);
                                fitSct(menu, null, sctHash, mapObj, name, menuJson);
                                topMenu.setSct(menu.getSct());
                            }
                            topMenu.setId(genraId(name + "_" + topMenuName));
                        }

                        topJson.put("data", topMenus);
                        String topString = topJson.toString();
                        Iterator<String> paramKeys = params.keySet().iterator();
                        while (paramKeys.hasNext()) {
                            String paramKey = paramKeys.next();
                            String value = params.getString(paramKey);
                            topString = topString.replaceAll(paramKey, value);
                        }

                        Iterator<String> bases = jsonObject.keySet().iterator();
                        while (bases.hasNext()) {
                            String paramKey = bases.next();
                            if (!"siteName".equals(paramKey)) {
                                String value = jsonObject.getString(paramKey);
                                paramKey = paramKey.replace("{", "\\{").replace("}", "\\}");
                                topString = topString.replaceAll(paramKey, value);
                            }
                        }
                        site.setTopMenu(topString);

                        //Long addId = saveSite(site, account);
                        Long addId = 100L;
                        if (!check(addId)) {
                            siteId.add(addId);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace().toString());
                System.out.println(e);
                error.add(jsonObject.getString("siteName"));
            }
        }
        JSONObject result = new JSONObject();
        result.put("success", siteId);
        result.put("error", error);
    }


    private boolean fitSct(Menu menu, String parentName, HashMap<String, HashMap<String, JSONObject>> sctHash, JSONObject mapObj, String baseName, HashMap<String, JSONArray> menuJson) {
        String name = menu.getName();
        String fitKey = null;
        if (!StringUtils.isEmpty(parentName)) {
            fitKey = parentName + "-" + name;
        } else {
            fitKey = name;
        }

        if (mapObj.containsKey(fitKey)) {
            JSONObject keyStr = mapObj.getJSONObject(fitKey);

            String base = baseName;

            if (!(keyStr == null) && keyStr.containsKey("base")) {
                String baseStr = keyStr.getString("base");
                if (!"lyra_current".equals(baseStr)) {
                    base = baseStr;
                }
            }
            HashMap<String, JSONObject> subSctHash = sctHash.get(base);

            if (!(keyStr == null) && keyStr.containsKey("include")) {
                List<String> keys = keyStr.getJSONArray("include").toJavaList(String.class);
                String key = fitKey(keys, subSctHash);
                if (!StringUtils.isEmpty(key)) {
                    JSONObject sctObj = subSctHash.get(key);
                    if (sctObj.containsKey("subMenus")) {
                        sctObj.remove("subMenus");
                    }

                    if (check(menu.getUrl()) && "custom".equals(menu.getType())) {
                        String clickUrl = sctObj.getString("clickUrl");
                        if (!clickUrl.startsWith("http") && !clickUrl.startsWith("https")) {
                            clickUrl = "http://ori.meizu.com" + clickUrl;
                        }
                        menu.setUrl(clickUrl);
                    }
                    menu.setId(genraId(sctObj.getString("privId")));
                    menu.setSct(sctObj);
                    menu.setIsStatistic(true);
                }
            } else if (!check(keyStr) && keyStr.containsKey("orclude")) {
                List<String> keys = keyStr.getJSONArray("orclude").toJavaList(String.class);
                String key = fitKey(keys, subSctHash);
                if (!check(key)) {
                    JSONObject sctObj = subSctHash.get(key);
                    if (sctObj.containsKey("subMenus")) {
                        sctObj.remove("subMenus");
                    }

                    if (check(menu.getUrl()) && "custom".equals(menu.getType())) {
                        String clickUrl = sctObj.getString("clickUrl");
                        if (!clickUrl.startsWith("http") && !clickUrl.startsWith("https")) {
                            clickUrl = "http://ori.meizu.com" + clickUrl;
                        }
                        menu.setUrl(clickUrl);
                    }
                    menu.setId(genraId(sctObj.getString("privId")));
                    menu.setSct(sctObj);
                    menu.setIsStatistic(true);
                } else {
                    return false;
                }
            } else if (!check(keyStr) && keyStr.containsKey("exclude")){
                List<Menu> menus = new ArrayList<>();
                List<String> keys = keyStr.getJSONArray("exclude").toJavaList(String.class);
                JSONArray jsonArray = menuJson.get(base);
                if (jsonArray != null) {
                    for (int i = 0; i <jsonArray.size(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        if (keys.contains(obj.getString("menuName"))) {
                            continue;
                        }
                        if (obj.getJSONArray("subMenus") == null || obj.getJSONArray("subMenus").size() == 0) {
                            Menu menu1 = generateMenu(obj, 3);
                            if (menu1 != null) {
                                menus.add(menu1);
                            }
                        } else {
                            Menu menu1 = new Menu();
                            menu1.setId(genraId(obj.getString("privId")));
                            menu1.setIsDefault(false);
                            String desc = obj.getString("menuDesc");
                            if ("ORI_MENU_HIDDEN".equals(desc)) {
//                                menu1.setIsHide(true);
                                continue;
                            } else {
                                menu1.setIsHide(false);
                            }
                            menu1.setName(obj.getString("menuName"));
                            menu1.setType("folder");
                            menu1.setLevel(3);
                            menu.setIsStatistic(true);
                            JSONArray jsonArray1 = obj.getJSONArray("subMenus");
                            generateSubMenu(jsonArray1, 4, menu1, keys);
                            if (menu1.getSub() != null && menu1.getSub().size() > 0) {
                                menus.add(menu1);
                            }
                        }
                    }
                }
                if (menus != null && menus.size() > 0) {
                    menu.setSub(menus);
                }
            }
        }

        if (check(menu.getId())) {
            menu.setId(genraId(baseName + "_" + name));
        }

        if (!check(menu.getSub())) {
            List<Menu> subs = menu.getSub();
            List<Menu> remove = new ArrayList<>();
            for (Menu menu1 : subs) {
                if (!fitSct(menu1, fitKey, sctHash, mapObj, baseName, menuJson)) {
                    synchronized (Test.class) {
                        remove.add(menu1);
                    }
                }
            }
            for (Menu menu2 : remove) {
                subs.remove(menu2);
            }
        }
        return true;
    }

    private String genraId(String menu) {
        String[] chars = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
                "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
                "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
                "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
                "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
                "U" , "V" , "W" , "X" , "Y" , "Z"
        };
        String id = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(menu.getBytes());
            String md5Str = new BigInteger(1, messageDigest.digest()).toString(16);
            String sTempSubString = md5Str.substring(0, 8);
            long lHexLong = 0x3FFFFFFF & Long.parseLong (sTempSubString, 16);
            for ( int j = 0; j < 8; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                id += chars[( int ) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void generateSubMenu(JSONArray jsonArray, int level, Menu menu, List<String> keys) {
        List<Menu> subs = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (keys.contains(jsonObject.getString("menuName"))) {
                continue;
            }

            Menu menu1 = new Menu();
            menu1.setId(genraId(jsonObject.getString("privId")));
            menu1.setIsDefault(false);
            menu1.setName(jsonObject.getString("menuName"));
            menu1.setLevel(level);
            menu1.setIsStatistic(true);
            String menuCode = jsonObject.getString("menuCode");
            if (jsonObject.getJSONArray("subMenus") == null || jsonObject.getJSONArray("subMenus").size() == 0) {
                menu1.setType("custom");
                String url =jsonObject.getString("clickUrl");
                if ((!TextUtils.isBlank(url) && url.equals("/" + menuCode)) || TextUtils.isBlank(url)) {
                    continue;
                }

                if (!url.startsWith("http") && !url.startsWith("https")) {
                    url = "http://ori.meizu.com" + url;
                }
                menu1.setUrl(url);
                if ("ORI_MENU_HIDDEN".equals(jsonObject.getString("menuDesc"))) {
//                    menu1.setIsHide(true);
                    continue;
                } else {
                    menu1.setIsHide(false);
                }
                menu1.setSct(jsonObject);
                subs.add(menu1);
            } else {
                menu1.setType("folder");
                menu1.setIsStatistic(true);
                JSONArray jsonArray1 = jsonObject.getJSONArray("subMenus");
                generateSubMenu(jsonArray1, level++, menu1, keys);
                if ((menu1.getSub()!= null) && (menu1.getSub().size() > 0)) {
                    subs.add(menu1);
                }
            }
        }
        if (subs.size() != 0) {
            menu.setSub(subs);
        }
    }


    private Menu generateMenu(JSONObject obj, int level) {
        String menuCode = obj.getString("menuCode");
        String url = obj.getString("clickUrl");

        if (!TextUtils.isBlank(url) && !url.equals("/" + menuCode)) {
            if (!url.startsWith("http") && !url.startsWith("https")) {
                url = "http://ori.meizu.com" + url;
            }

            Menu menu = new Menu();
            menu.setUrl(url);
            menu.setId(genraId(obj.getString("privId")));
            String desc = obj.getString("menuDesc");
            if ("ORI_MENU_HIDDEN".equals(desc)) {
                return null;
            } else {
                menu.setIsHide(false);
            }
            menu.setLevel(level);
            menu.setIsDefault(false);
            menu.setName(obj.getString("menuName"));
            menu.setType("custom");
            menu.setSct(obj);
            menu.setIsStatistic(true);
            return menu;
        }
        return null;
    }

    private String fitKey(List<String> keyList, HashMap<String, JSONObject> sctHash) {
        if (!check(sctHash)) {
            Iterator<String> srcKeys = sctHash.keySet().iterator();
            while (srcKeys.hasNext()) {
                String srcKey = srcKeys.next();
                for (String destKey : keyList) {
                    if (srcKey.matches(destKey)) {
                        return srcKey;
                    }
                }
            }
        }

        return null;
    }


}
