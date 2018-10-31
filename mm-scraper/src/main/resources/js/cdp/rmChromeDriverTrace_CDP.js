(function() {
    for (const documentKey in window['document']) {
        if (documentKey.match(/\$[a-z]dc_/) && window['document'][documentKey]['cache_']) {
            console.log(documentKey);
            delete window['document'][documentKey];
            return true;
        }
    }
    return false;
})();
(function() {
    if (navigator.webdriver) {
        Object.defineProperty(navigator, 'webdriver', {
            get: () => undefined
        });
    }
})();