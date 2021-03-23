import requests
import json
url="https://xueqiu.com/query/v1/symbol/search/status"
header={ #不用变

    "Cookie":"acw_tc=2760820116163387777013664e5d42c0d58fcf37324ad715ca3c9d68673658; xq_a_token=a4b3e3e158cfe9745b677915691ecd794b4bf2f9; xqat=a4b3e3e158cfe9745b677915691ecd794b4bf2f9; xq_r_token=b80d3232bf315f8710d36ad2370bc777b24d5001; xq_id_token=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJ1aWQiOi0xLCJpc3MiOiJ1YyIsImV4cCI6MTYxNzc2MzQxOCwiY3RtIjoxNjE2MzM4NzQ2Nzc5LCJjaWQiOiJkOWQwbjRBWnVwIn0.CkYjbwRvNZIfh4ZTxDD-Q02rglFmdsxeDQrk9zFJQCS9g4_htV62Xyfpgv2YTh0sVAIZvv6TMtt905Eu-vBjrSYTcuG61e0pE3n6OZgZPpI7EBmozvI_Qeuro9CdpKq9XBcfoaGM_tSBnekhpDCSmSmkEKp7bieQgJpN4lYD0qjkZtROwKSHrsiv3jEX8P3gcM5L3EahyAVRTzeyF4lNyABS3gD7uUHNvwv3O6jnaN0BeUsEvjUg6hneJkIcDlms27cRfwXDTYOMXb04JqUfeKzrmdZEhJeEOUdld4IDuy9nCklZ1cKWNunI7L1MHIsbB7VbJrXFt2nvesgKWmAEJg; u=861616338777721; Hm_lvt_1db88642e346389874251b5a1eded6e3=1616338779; device_id=bd43492b7a85e5d94101bda778256023; is_overseas=0; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1616339212",
    "Referer":"https://xueqiu.com/S/SZ002353",
    "User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.72 Safari/537.36",
}
params={                                #必带信息 都可调

    "symbol":"SH600415", #股票标识码
    "source":"all", #评论筛选 all=全部 user=讨论 trans=交易 自选股新闻=资讯 公告=公告 研报=研报
    "sort":"time",  #按什么排列 time 时间 alpha 热度
    "page":"1", #页码 目前看都有100页

    }

req=requests.get(url,params,headers=header).content.decode('utf-8');     #解码，并且去除str中影响json转换的字符（\n\rjsonp(...)）;
result=json.loads(req);
maxPage=result['maxPage']
count=1
for i in range(0,len(result['list'])):
    print("爬取第",count,"个评论")
    count=count+1
    print(result['list'][i]['text'])
for page in range(2,maxPage+1):
    params["page"]=str(page);
    req=requests.get(url,params,headers=header).content.decode('utf-8');     #解码，并且去除str中影响json转换的字符（\n\rjsonp(...)）;
    result=json.loads(req);
    for i in range(0,len(result['list'])):
        print("爬取第",count,"个评论")
        count=count+1
        print(result['list'][i]['text'])