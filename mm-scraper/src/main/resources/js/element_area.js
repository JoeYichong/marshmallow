function getElementLocation(e) {
    var rect = e.getBoundingClientRect();
    return Math.round(rect.left + (window.scrollX || document.documentElement.scrollLeft || 0)) + ":" +
        Math.round(rect.top + (window.scrollY || document.documentElement.scrollTop || 0)) + ":" +
        Math.round(rect.width) + ":" +
        Math.round(rect.height);
}
// noinspection JSAnnotator
return getElementLocation(document.querySelector(arguments[0]));
