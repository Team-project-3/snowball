import requests
import json
import sys
#import os
import xlwt
import re
import xlrd
from xlutils.copy import copy

url="https://xueqiu.com/query/v1/symbol/search/status"
header={ #不用变

    "Cookie":"acw_tc=2760820116163387777013664e5d42c0d58fcf37324ad715ca3c9d68673658; xq_a_token=a4b3e3e158cfe9745b677915691ecd794b4bf2f9; xqat=a4b3e3e158cfe9745b677915691ecd794b4bf2f9; xq_r_token=b80d3232bf315f8710d36ad2370bc777b24d5001; xq_id_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1aWQiOi0xLCJpc3MiOiJ1YyIsImV4cCI6MTYxNzc2MzQxOCwiY3RtIjoxNjE2MzM4NzQ2Nzc5LCJjaWQiOiJkOWQwbjRBWnVwIn0.CkYjbwRvNZIfh4ZTxDD-Q02rglFmdsxeDQrk9zFJQCS9g4_htV62Xyfpgv2YTh0sVAIZvv6TMtt905Eu-vBjrSYTcuG61e0pE3n6OZgZPpI7EBmozvI_Qeuro9CdpKq9XBcfoaGM_tSBnekhpDCSmSmkEKp7bieQgJpN4lYD0qjkZtROwKSHrsiv3jEX8P3gcM5L3EahyAVRTzeyF4lNyABS3gD7uUHNvwv3O6jnaN0BeUsEvjUg6hneJkIcDlms27cRfwXDTYOMXb04JqUfeKzrmdZEhJeEOUdld4IDuy9nCklZ1cKWNunI7L1MHIsbB7VbJrXFt2nvesgKWmAEJg; u=861616338777721; Hm_lvt_1db88642e346389874251b5a1eded6e3=1616338779; device_id=bd43492b7a85e5d94101bda778256023; is_overseas=0; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1616339212",
    "User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.72 Safari/537.36",
}
params={                                #必带信息 都可调

    "symbol":"", #股票标识码
    "source":"user", #评论筛选 all=全部 user=讨论 trans=交易 自选股新闻=资讯 公告=公告 研报=研报
    "sort":"time",  #按什么排列 time 时间 alpha 热度
    "page":"1", #页码 目前看都有100页

    }
#保存为txt
'''
req=requests.get(url,params,headers=header).content.decode('utf-8');     #解码，并且去除str中影响json转换的字符（\n\rjsonp(...)）;
result=json.loads(req);
maxPage= result['maxPage']
count=1
data=open("爬虫结果.txt",'w+',encoding='utf-8') 
for i in range(0,len(result['list'])):
    print("正在爬取第",count,"个评论")
    print(result['list'][i]['text'],file=data)
    count=count+1
for page in range(2,maxPage+1):
    params["page"]=str(page);
    req=requests.get(url,params,headers=header).content.decode('utf-8');     #解码，并且去除str中影响json转换的字符（\n\rjsonp(...)）;
    result=json.loads(req);
    for i in range(0,len(result['list'])):
        print("正在爬取第",count,"个评论")
        print(result['list'][i]['text'],file=data)
        count=count+1
print("爬取完毕")
'''


#保存为excel
"""
req=requests.get(url,params,headers=header).content.decode('utf-8');     #解码，并且去除str中影响json转换的字符（\n\rjsonp(...)）;
result=json.loads(req);
maxPage=result['maxPage']
total=1
xls = xlwt.Workbook()
sheet = xls.add_sheet('sample')
sheet.write(0,0,"用户名")
sheet.write(0,1,"时间")
sheet.write(0,2,"内容")
for i in range(0,len(result['list'])):
    print("正在爬取第1页第",i+1,"个评论")
    sheet.write(total,0,result['list'][i]['user']['screen_name'])
    sheet.write(total,1,result['list'][i]['timeBefore'])
    pattern = re.compile(r'<[^>]+>',re.S)
    tmp = pattern.sub('', result['list'][i]['text'])   #去除评论里的html标签
    sheet.write(total, 2, tmp)
    total=total+1
    i=i+1
for page in range(2,maxPage+1):
    params["page"]=str(page);
    req=requests.get(url,params,headers=header).content.decode('utf-8');     #解码，并且去除str中影响json转换的字符（\n\rjsonp(...)）;
    result=json.loads(req);
    for i in range(0,len(result['list'])):
        print("正在爬取第",page,"页第",i+1,"个评论")
        sheet.write(total,0,result['list'][i]['user']['screen_name'])
        sheet.write(total,1,result['list'][i]['timeBefore'])
        pattern = re.compile(r'<[^>]+>',re.S)
        tmp = pattern.sub('', result['list'][i]['text'])
        sheet.write(total, 2, tmp)
        total=total+1
        i=i+1
name=params["symbol"]
path=".\\评论\\"+name+".xls"
xls.save(path)
print("爬取完毕")
"""

#断点续传
def func(code):
    params["symbol"]=code
    req=requests.get(url,params,headers=header).content.decode('utf-8');     #解码，并且去除str中影响json转换的字符（\n\rjsonp(...)）;
    result=json.loads(req);
    maxPage=result['maxPage']
    name=params["symbol"]
    path=".\\评论\\"+name+".xls"

    try:
        rb = xlrd.open_workbook(path)
    except FileNotFoundError:
        xls = xlwt.Workbook()
        sheet = xls.add_sheet('sample')
        sheet.write(0,0,"用户名")
        sheet.write(0,1,"时间")
        sheet.write(0,2,"内容")
        xls.save(path)
        rb = xlrd.open_workbook(path)

    sheets = rb.sheet_names()  # 获取工作簿中的所有工作表名字，形成列表元素
    sheet_old = rb.sheet_by_name(sheets[0])  # 通过sheets[0]工作表名称获取工作簿中所有工作表中的的第一个工作表
    rows_old = sheet_old.nrows
    page_old = (rows_old-1)//10
    total=rows_old
    wb = copy(rb)
    sheet = wb.get_sheet(0)

    for page in range(page_old+1,maxPage+1):
        params["page"]=str(page);
        req=requests.get(url,params,headers=header).content.decode('utf-8');     #解码，并且去除str中影响json转换的字符（\n\rjsonp(...)）;
        result=json.loads(req);
        for i in range(0,len(result['list'])):
            print("正在爬取第",page,"页第",i+1,"个评论")
            sheet.write(total,0,result['list'][i]['user']['screen_name'])
            sheet.write(total,1,result['list'][i]['timeBefore'])
            pattern = re.compile(r'<[^>]+>',re.S)
            tmp = pattern.sub('', result['list'][i]['text'])
            sheet.write(total, 2, tmp)
            total=total+1
            i=i+1
        wb.save(path)

    print("爬取完毕")


if __name__ == '__main__':
    code=sys.argv[1]#java调用传进来的参数
    func(code)
    #func("SH600415")