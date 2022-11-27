// HOST 配置
var paramStatisticsHost = "Statistics-Host";
var paramStatisticsKey = "Statistics-Key";
var paramStatisticsSecret = "Statistics-Secret";
var paramStatisticsBrowTag = "Statistics-Brow-Tag";
var paramStatisticsBrowAttr = "Statistics-Brow-Attr";
// 授权配置
function auth(httpRequest) {
    let elements = getElement("script", paramStatisticsKey, paramStatisticsSecret);
    if (elements && elements.length > 0) {
        httpRequest.setRequestHeader("Statistics-Key", elements[0].getAttribute(paramStatisticsKey));
        httpRequest.setRequestHeader("Statistics-Secret", elements[0].getAttribute(paramStatisticsSecret));
    }
}
// Host 配置
function statisticsHost() {
    let elements = getElement("script", paramStatisticsHost);
    if (elements && elements.length > 0)
        return elements[0].getAttribute(paramStatisticsHost);
    return '';
}
// 浏览统计 URL
function statisticsBrowUrl() {
    return statisticsHost() + '/statistics/browse';
}
// 统计查询 URL
function statisticsQueryUrl() {
    return statisticsHost() + '/statistics/query';
}
// 执行浏览统计
function doStatistics() {
    let httpRequest = new XMLHttpRequest();
    httpRequest.open('POST', statisticsBrowUrl(), true);
    let param = {
        blogUrl: window.location.href
    };
    auth(httpRequest, param);
    httpRequest.setRequestHeader("Content-type","application/json");
    httpRequest.send(JSON.stringify(param));
}
doStatistics();

function getElement(tag, ...dataAttr) {
    let result = [];
    if (dataAttr && dataAttr.length === 0) {
        return result;
    }
    let tagElements = document.getElementsByTagName(tag);
    if (!dataAttr) {
        return tagElements;
    }
    for (const element of tagElements) {
        let match = true;
        for (let dateAttrItem of dataAttr) {
            if (!element.hasAttribute(dateAttrItem)) match = false;
        }
        if (match)
            result.push(element);
    }
    return result;
}
function getElementAttr(elements, dataAttr) {
    let result = [];
    if (elements && elements.length > 0) {
        for (let element of elements) {
            let attrValue = element.getAttribute(dataAttr);
            if (attrValue)
                result.push(attrValue);
        }
    }
    return result;
}
function queryStatistics(param, success) {
    let httpRequest = new XMLHttpRequest();
    httpRequest.open('POST', statisticsQueryUrl(), true);
    auth(httpRequest, param);
    httpRequest.setRequestHeader("Content-type","application/json");
    httpRequest.send(JSON.stringify(param));
    httpRequest.onreadystatechange = function () {
        if (httpRequest.status === 200) {
            let json = httpRequest.responseText;
            if (json && json !== '') {
                let result = JSON.parse(json);
                success(result);
            }
        }
    };
}
function query(tage, dataAttr) {
    let elements = getElement(tage, dataAttr);
    let param = getElementAttr(elements, dataAttr);
    queryStatistics(param, data => {
        for (let element of elements) {
            element.innerHTML = data[element.getAttribute(dataAttr)];
        }
    });
}
let elements = getElement("script", paramStatisticsBrowTag, paramStatisticsBrowAttr);
if (elements && elements.length > 0) {
    query(elements[0].getAttribute(paramStatisticsBrowTag), elements[0].getAttribute(paramStatisticsBrowAttr));
}