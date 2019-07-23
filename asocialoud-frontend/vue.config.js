module.exports = {
    runtimeCompiler: true,
    devServer: {
        open: process.platform === 'darwin',
        host: 'localhost',
        port: 6600,
        /*proxy: {
            '/': {
                target: 'http://localhost:8070', // this configuration needs to correspond to the Spring Boot backends' application.properties server.port
                ws: true,
                changeOrigin: true
            }
        },*/
        https: false,
        hotOnly: false,
    },
};