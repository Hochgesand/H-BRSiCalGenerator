module.exports = {
    purge: ['./src/**/*.{js,jsx,ts,tsx}', './public/index.html'],
    darkMode: false, // or 'media' or 'class'
    theme: {
        extend: {},
    },
    variants: {
        extend: {},
    },
    content: [
        './pages/**/*.{html,js}',
        './components/**/*.{html,js}',
    ],
    plugins: [
        require('daisyui'),
    ],
}
