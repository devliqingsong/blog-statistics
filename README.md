# blog-statistics
浏览统计是一个简单的静态网站浏览统计软件，初衷是给自己的静态博客添加一个可自我定制的统计功能。目前就开发了这个 Demo 一样的作品，后续可能会继续丰富其功能。

当前有的功能只是最简单的浏览统计效果
- 文件结合 Map 存储统计数据
- 使用配置文件配置授权
- PV、UV 等策略虽然预留了扩展但是都未做任何实现

# 使用示例
```html
<span pv-statistics-url="https://www.devnolo.com/blog.html" ></span> 浏览
<script type="text/javascript" src="http://localhost:8080/blog.statistics.js" Statistics-Host="https://www.devnolo.com" Statistics-Key="授权 Key" Statistics-Secret="授权 Secret" Statistics-Brow-Tag="span" Statistics-Brow-Attr="pv-statistics-url"></script>
```

script 上自定义参数说明
- Statistics-Host
  - JS 访问的统计服务地址，可以自定义
- Statistics-Key 和 Statistics-Secret
  - 授权的 Key 和 Secret
  - 一般是成对出现，在统计服务的代码中配置的 `blog.statistics.authentication` 配置项
    - `blog.statistics.authentication=authKeyA:authSecretA;authKeyB:authSecretB`
    - `:` 分隔 Key 和 Secret
    - `;` 分隔多对授权配置
- Statistics-Brow-Tag
  - 统计值需要写入的标签
- Statistics-Brow-Attr
  - 统计需要的唯一标识（目前是以当前页面的 URL 作为唯一标识）