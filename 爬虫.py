import requests
import json
import sys
import random
import time
import xlwt
import re
import xlrd
import sys
import io
from xlutils.copy import copy

sys.stdout = io.TextIOWrapper(sys.stdout.buffer,encoding='utf8') 
url="https://xueqiu.com/query/v1/symbol/search/status"

header={ #不用变

    "Cookie":"acw_tc=2760820616200600588976914e4ad2d393a04439df937b7c74af12ca5ce111; xq_a_token=4b4d3f5b97e67b975f4e1518dc4c417ebf0ad4c4; xqat=4b4d3f5b97e67b975f4e1518dc4c417ebf0ad4c4; xq_r_token=960e1d453ab676f85fa80d2d41b80edebfde8cc0; xq_id_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1aWQiOi0xLCJpc3MiOiJ1YyIsImV4cCI6MTYyMjUxNTc5MiwiY3RtIjoxNjIwMDYwMDQ4ODI1LCJjaWQiOiJkOWQwbjRBWnVwIn0.TSeGTRmFh2-ZnefcZzB5x3dqzAk9yrY9G4uea4fIIXAxle4gF2mCxS245fvtUFMXCPErPDK1QFIPEW4bpEub947dTjePh0dhkJ4YXe78bBLLE1Qb7B-YYJGw6pJE-boTqsAzXbSCzEtH8wXwhnQc_arfb-C7uwjwTtKQVEzTxKof9zp6vzPF310gdov0mk6d6TLCmIWzG3z2nNuYdm7wWxjNlG_FtlP7DXgdTET2VSEHo2twEgltDdXoAucGUbfVJYJ-UAoMDWo7tfTJhUz2St8J5jMF5s-CIMB4ah6bwjr0KuvbTEoTj_f-9Uh_zHWM8uCE0SeLhsf43jNNGAqayw; u=671620060058897; is_overseas=0; device_id=201458cc65ab478220f440ecb0f3be87; Hm_lvt_1db88642e346389874251b5a1eded6e3=1620060066; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1620060066",
    "User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.72 Safari/537.36"
   
}
params={                                #必带信息 都可调

    "symbol":"", #股票标识码
    "source":"user", #评论筛选 all=全部 user=讨论 trans=交易 自选股新闻=资讯 公告=公告 研报=研报
    "sort":"time",  #按什么排列 time 时间 alpha 热度
    "page":"1", #页码 目前看都有100页
 
    }

#断点续传
def func(code):
    params["symbol"]=code
    req=requests.get(url,params,headers=header).content.decode('utf-8');     #解码，并且去除str中影响json转换的字符（\n\rjsonp(...)）;
    result=json.loads(req);
    maxPage=result['maxPage']
    name=params["symbol"]
    path=name+".xls"

    try:
        rb = xlrd.open_workbook(path)
    except FileNotFoundError:
        xls = xlwt.Workbook()
        sheet = xls.add_sheet('sample')
        sheet.write(0,0,"用户名")
        sheet.write(0,1,"时间")
        sheet.write(0,2,"页码")
        sheet.write(0,3,"内容")
        xls.save(path)
        rb = xlrd.open_workbook(path)

    sheets = rb.sheet_names()  # 获取工作簿中的所有工作表名字，形成列表元素
    sheet_old = rb.sheet_by_name(sheets[0])  # 通过sheets[0]工作表名称获取工作簿中所有工作表中的的第一个工作表
    rows_old = sheet_old.nrows
    if rows_old != 1 :
        page_old = int(sheet_old.row(rows_old-1)[2].value);
    else:
        page_old = 0
    total=rows_old
    wb = copy(rb)
    sheet = wb.get_sheet(0)

    for page in range(page_old+1,maxPage+1):
        params["page"]=str(page);
        #time.sleep(5);
        req=requests.get(url,params,headers=header).content.decode('utf-8');     #解码，并且去除str中影响json转换的字符（\n\rjsonp(...)）;
        result=json.loads(req);
        for i in range(0,len(result['list'])):
            print(code,":正在爬取第",page,"页第",i+1,"个评论")
            sheet.write(total,0,result['list'][i]['user']['screen_name'])
            sheet.write(total,1,result['list'][i]['timeBefore'])
            sheet.write(total,2,page)
            pattern = re.compile(r'<[^>]+>',re.S)
            tmp = pattern.sub('', result['list'][i]['text'])
            sheet.write(total, 3, tmp)
            total=total+1
            i=i+1
        wb.save(path)

    print(code,":爬取完毕")


if __name__ == '__main__':
    code=sys.argv[1]#java调用传进来的参数
    func(code)
    #func("SH600415")