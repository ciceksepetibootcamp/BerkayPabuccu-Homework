[![-----------------------------------------------------](
https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/aqua.png)](https://github.com/berkaypab?tab=repositories)
<br/>

<h1>Test Spec File </h1></br>


<article class='markdown-body'><meta charset="utf-8"><link rel="stylesheet" type="text/css" href="..\style.css"><p>Specification Heading
=====================
Created by berkay on 22.11.2021

This is an executable specification file which follows markdown syntax.
Every heading in this file denotes a scenario. Every bulleted point denotes a step.

Ciceksepeti Sample
-------------------</p>
<ul>
<li>User navigates to <a href="https://ciceksepeti.com" rel="nofollow">https://ciceksepeti.com</a></li>
<li>Close Popups</li>
<li>Sign In</li>
<li>Search Product</li>
<li>Add Product to Wishlist</li>
<li>Compare Product Detail Page Price and Cart Price</li>
</ul>
</article>

<hr>

<h1>Test Concept File </h1></br>

<article class='markdown-body'><meta charset="utf-8"><link rel="stylesheet" type="text/css" href="..\style.css"><p>Created by berkay on 23.11.2021

This is a concept file with following syntax for each concept.</p>
<h3>User navigates to <a href="https://ciceksepeti.com" rel="nofollow">https://ciceksepeti.com</a></h3>
<ul>
<li>Navigate to home page</li>
<li>Check if title contains text &quot;ÇiçekSepeti - Online Çiçek &amp; Hediye Sitesi&quot;</li>
</ul>
<h3>Close Popups</h3>
<ul>
<li>Switch to frame given &quot;popUpFrame&quot;</li>
<li>Click to element &quot;closePopUp&quot;</li>
<li>Switch to default frame</li>
<li>Click to element &quot;orderFieldClosePopUp&quot;</li>
</ul>
<h3>Sign In</h3>
<ul>
<li>Hover to element &quot;signInButtonHover&quot;</li>
<li>Click to element &quot;signIn&quot;</li>
<li>&quot;userIDEmail&quot; li elemente &quot;senem2142@<a href="http://outlook.com" rel="nofollow">outlook.com</a>&quot; değerini yazdır</li>
<li>&quot;userPassword&quot; li elemente &quot;Test1234&quot; değerini yazdır</li>
<li>Click to element &quot;loginButton&quot;</li>
<li>Set captcha code</li>
</ul>
<h3>Search Product</h3>
<ul>
<li>&quot;searchBox&quot; li elemente &quot;logitech mouse&quot; değerini yazdır ve enter'a bas</li>
<li>&quot;products&quot; menu listesinden rasgele seç</li>
<li>&quot;productDetailPagePriceIntegerPart&quot; li elementi bul ve değerini &quot;detailPagePriceIntegerPart&quot; olarak sakla</li>
<li>&quot;productDetailPagePriceDecimalPart&quot; li elementi bul ve değerini &quot;detailPagePriceDecimalPart&quot; olarak sakla</li>
</ul>
<h3>Add Product to Wishlist</h3>
<ul>
<li>Click to element &quot;addToWishList&quot;</li>
<li>Click to element &quot;addToCart&quot;</li>
</ul>
<h3>Compare Product Detail Page Price and Cart Price</h3>
<ul>
<li>&quot;productCartPrice&quot; li elementi bul ve değerini &quot;detailPagePriceIntegerPart&quot; ve &quot;detailPagePriceDecimalPart&quot; saklanan değer ile karşılaştır</li>
</ul>
</article>











You can see the result of runnings on "/reports/html-report/2021-11-24 03.39.43/index.html"

## About this template

This is a template to get started with a Gauge project that uses Selenium as the driver to interact with a web browser.

## Installing this template

    gauge --install java_maven_selenium

## Building on top of this template

### Defining Specifications

* This template includes a sample specification which opens up a browser and navigates to `Get Started` page of Gauge.
* Add more specifications on top of sample specification.

Read more about [Specifications](http://getgauge.io/documentation/user/current/specifications/README.html)

### Writing the implementations

This is where the java implementation of the steps would be implemented. Since this is a Selenium based project, the java implementation would invoke Selenium APIs as required.

_We recommend considering modelling your tests using the [Page Object](https://github.com/SeleniumHQ/selenium/wiki/PageObjects) pattern, and the [Webdriver support](https://github.com/SeleniumHQ/selenium/wiki/PageFactory) for creating them._

- Note that every Gauge step implementation is annotated with a `Step` attribute that takes the Step text pattern as a parameter.
  Read more about [Step implementations in Java](http://getgauge.io/documentation/user/current/test_code/java/java.html)

### Execution

* You can execute the specification as:

```
mvn test
```

[![-----------------------------------------------------](
https://raw.githubusercontent.com/andreasbm/readme/master/assets/lines/aqua.png)](https://github.com/berkaypab?tab=repositories)
<br/>
