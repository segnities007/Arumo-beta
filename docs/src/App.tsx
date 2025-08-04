import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Layout from './components/Layout'
import Home from './pages/Home'
import RepositoryInterfaces from './pages/RepositoryInterfaces'
import DataModels from './pages/DataModels'
import Enums from './pages/Enums'
import MviArchitecture from './pages/MviArchitecture'
import Examples from './pages/Examples'

function App() {
  return (
    <Router>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/repository-interfaces" element={<RepositoryInterfaces />} />
          <Route path="/data-models" element={<DataModels />} />
          <Route path="/enums" element={<Enums />} />
          <Route path="/mvi-architecture" element={<MviArchitecture />} />
          <Route path="/examples" element={<Examples />} />
        </Routes>
      </Layout>
    </Router>
  )
}

export default App 