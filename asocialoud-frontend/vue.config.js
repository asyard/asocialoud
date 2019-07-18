module.exports = {
    runtimeCompiler: true,
    devServer: {
        open: process.platform === 'darwin',
        host: '0.0.0.0',
        port: 6600,
        https: false,
        hotOnly: false,
    },
};