import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import { Routes, Route } from 'react-router-dom'
import SignUpPage from './pages/signUpPage'
// import './App.css'

function App() {
  return (
    <>
      <header></header>
      <main>
        <Routes>
          <Route path="/" element={<SignUpPage />} />
        </Routes>
      </main>
      <footer></footer>
    </>
  )
}

export default App
