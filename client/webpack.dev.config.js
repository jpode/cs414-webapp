const path = require('path')
const webpack = require('webpack')

const port = 8088;
const url = "http//localhost:" + port;

module.exports = {
  devtool: 'source-map',
  devServer: {
    port: port,
    open: true,
    hot: true
  },
  entry: [
    'babel-polyfill',
    'react-hot-loader/patch',
    './src/entry.jsx'
  ],
  output: {
    path: path.join(__dirname, '/'),
    filename: 'bundle.js',
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': {
        'SERVICE_URL': JSON.stringify(url)
      }
    }),
    new webpack.NamedModulesPlugin(),
    new webpack.HotModuleReplacementPlugin()
  ],
  module: {
    loaders: [
      { 
        test: /\.(js|jsx)/, loader: 'babel-loader',
        include: path.join(__dirname, './', 'src'),
        query: { presets: ['react'] } // Still unfamiliar with the query instruction
      },
      { test: /\.css$/, loader: 'style!css' },
      { test: /\.scss$/, loaders: ["style-loader", "css-loader", "sass-loader"] }
    ]
  }
}
