<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>jQuery MultiSelect Widget Demo</title>

<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/jquery.multiselect.filter.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/style.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%= request.getContextPath()%>/resources/css/multiselect/prettify.css" />

<link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/${theme.name}/jquery-ui.css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.js"></script>

<script src="<%= request.getContextPath()%>/js/jquery.multiselect.js"></script>
<script src="<%= request.getContextPath()%>/js/jquery.multiselect.filter.js"></script>
<script src="<%= request.getContextPath()%>/js/prettify.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/bloqueaTecla.js?n=1"></script>

</head>
<body onload="prettyPrint();">

<h2>Filter Plugin</h2>

<p>Filtering is available by including the <a href="http://github.com/ehynds/jquery-ui-multiselect-widget/raw/master/src/jquery.multiselect.filter.js">jquery.multiselect.filter.js</a> plugin and the <a href="http://github.com/ehynds/jquery-ui-multiselect-widget/raw/master/jquery.multiselect.filter.css">jquery.multiselect.filter.css</a> CSS file.  Initialize filtering on any of your multiselects by calling <code class="prettyprint">multiselectfilter()</code> on the widget.</p>

<pre class="prettyprint">
$("select").multiselect().multiselectfilter();
</pre>

<form style="margin:20px 0">
	<p>
		<select multiple="multiple" size="5" style="width:370px">
		<option value="black">Black</option>
		<option value="blue">Blue</option>
		<option value="brown">Brown</option>
		<option value="green">Green</option>
		<option value="orange">Orange</option>
		<option value="purple">Purple</option>
		<option value="red">Red</option>
		<option value="yellow">Yellow</option>
		</select>
	</p>
	<p>
		<select multiple="multiple" size="5" style="width:370px">
		<optgroup label="test">
			<option value="blue">Blue</option>
			<option value="green">Green</option>
			<option value="red">Red</option>
		</optgroup>
		<optgroup label="foo">
			<option value="black">Black</option>
			<option value="brown">Brown</option>
			<option value="orange">Orange</option>
			<option value="purple">Purple</option>
			<option value="yellow">Yellow</option>
		</optgroup>
		</select>
	</p>
</form>

<h3>Options:</h3>

<p>Pass any of these as a configuration object when you initialize <code>multiselectfilter()</code>:</p>
<ul>
<li><code><strong>label</strong></code><p>The text to appear left of the input.  Defaults to &quot;Filter:&quot;</p></li>
<li><code><strong>width</strong></code><p>The width of the input in pixels.  Defaults to 100px in the style sheet, but you can override this for each instance.</p></li>
<li><code><strong>placeholder</strong></code><p>The HTML5 placeholder attribute value of the input.  Only supported in webkit as of this writing.  Defaults to &quot;Enter keywords&quot;</p></li>
</ul>

<h3>Events:</h3>
<ul>
<li><code><strong>filter</strong></code>
	<p>A callback function that fires after filtering is complete.  Accepts two arguments: the original event and an array of matches.</p>
	
	<p>To do something when no matches are found:</p>
	
<pre class="prettyprint">
$("select").multiselect().multiselectfilter({
    filter: function(event, matches){
        if( !matches.length ){
            // do something
        }
    }
});
</pre>

	<p>To do something with a match:</p>
	
<pre class="prettyprint">
$("select").multiselect().multiselectfilter({
    filter: function(event, matches){
		
        // find the first matching checkbox
        var first_match = $( matches[0] );
    }
});
</pre>

	<p>You can also bind to the event after a multiselect has been initialized, like such:
	
<pre class="prettyprint">
$("select").bind("multiselectfilterfilter", function(event, matches){
    // do something
});
</pre>
	
</li>
</ul>

<h3>Methods</h3>
<p>Syntax: <code class="prettyprint">$("select").multiselectfilter("method_name");</code></p>
<ul>
<li><code><strong>updateCache</strong></code><p>Reloads the cache of values to search against.  Make sure you call this after dynamically adding or removing any inputs to the multiselect.</p></li>
<li><code><strong>destroy</strong></code><p>Destroys the widget.</p></li>
<li><code><strong>widget</strong></code><p>Returns the wrapper div with the input and label text inside.  This is a quick and easy way to access the HTML created by the plugin.</p></li>
</ul>

<script type="text/javascript">
$("select").multiselect().multiselectfilter();
</script>

</body>
</html>
