// detect browsers
config.frameworks.push("detectBrowsers");
config.set({
    browsers: [],
    detectBrowsers: {
        enabled: true,
        usePhantomJS: false,
        preferHeadless: true,
        postDetection: function(browsers) {
            browsers = browsers.filter((browser) => browser.includes("Headless"))
            return browsers;
        }
    }
});
config.plugins.push("karma-detect-browsers");

// set timeout (20s)
config.set({
    browserDisconnectTimeout: 20000
})