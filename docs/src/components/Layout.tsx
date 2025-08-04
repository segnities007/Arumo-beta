import { useState } from 'react'
import { Link, useLocation } from 'react-router-dom'
import { 
  Home, 
  Code, 
  Database, 
  List, 
  Sitemap, 
  Lightbulb, 
  Menu, 
  X,
  Github,
  User
} from 'lucide-react'

interface LayoutProps {
  children: React.ReactNode
}

const navigation = [
  {
    title: 'Getting Started',
    items: [
      { name: 'Introduction', href: '/', icon: Home },
      { name: 'Quick Start', href: '/quick-start', icon: Home },
      { name: 'Installation', href: '/installation', icon: Home },
    ]
  },
  {
    title: 'API Reference',
    items: [
      { name: 'Repository Interfaces', href: '/repository-interfaces', icon: Code },
      { name: 'Data Models', href: '/data-models', icon: Database },
      { name: 'Enums', href: '/enums', icon: List },
      { name: 'MVI Architecture', href: '/mvi-architecture', icon: Sitemap },
    ]
  },
  {
    title: 'Examples',
    items: [
      { name: 'Code Examples', href: '/examples', icon: Lightbulb },
      { name: 'Tutorials', href: '/tutorials', icon: Lightbulb },
    ]
  },
  {
    title: 'Resources',
    items: [
      { name: 'GitHub Repository', href: 'https://github.com/segnities007/Arumobeta', icon: Github, external: true },
      { name: 'Developer Profile', href: 'https://github.com/segnities007', icon: User, external: true },
    ]
  }
]

export default function Layout({ children }: LayoutProps) {
  const [sidebarOpen, setSidebarOpen] = useState(false)
  const location = useLocation()

  return (
    <div className="flex h-screen bg-gray-50">
      {/* Sidebar */}
      <div className={`
        fixed inset-y-0 left-0 z-50 w-64 bg-white border-r border-gray-200 transform transition-transform duration-300 ease-in-out
        ${sidebarOpen ? 'translate-x-0' : '-translate-x-full'}
        lg:translate-x-0 lg:static lg:inset-0
      `}>
        <div className="flex items-center justify-between h-16 px-6 border-b border-gray-200">
          <Link to="/" className="flex items-center space-x-2">
            <div className="w-8 h-8 bg-gradient-to-br from-primary-500 to-accent-500 rounded-lg flex items-center justify-center text-white font-bold">
              A
            </div>
            <span className="text-xl font-bold text-gray-900">Arumo-beta</span>
          </Link>
          <button
            onClick={() => setSidebarOpen(false)}
            className="lg:hidden p-2 rounded-md text-gray-400 hover:text-gray-500 hover:bg-gray-100"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        <nav className="flex-1 px-4 py-6 space-y-8 overflow-y-auto">
          {navigation.map((section) => (
            <div key={section.title}>
              <h3 className="px-2 text-xs font-semibold text-gray-500 uppercase tracking-wider">
                {section.title}
              </h3>
              <div className="mt-2 space-y-1">
                {section.items.map((item) => {
                  const Icon = item.icon
                  const isActive = location.pathname === item.href
                  
                  return (
                    <Link
                      key={item.name}
                      to={item.href}
                      target={item.external ? '_blank' : undefined}
                      rel={item.external ? 'noopener noreferrer' : undefined}
                      className={`
                        group flex items-center px-2 py-2 text-sm font-medium rounded-md transition-colors duration-200
                        ${isActive 
                          ? 'bg-primary-50 text-primary-700 border-r-2 border-primary-500' 
                          : 'text-gray-600 hover:text-gray-900 hover:bg-gray-50'
                        }
                      `}
                      onClick={() => setSidebarOpen(false)}
                    >
                      <Icon className={`
                        mr-3 h-5 w-5 transition-colors duration-200
                        ${isActive ? 'text-primary-500' : 'text-gray-400 group-hover:text-gray-500'}
                      `} />
                      {item.name}
                    </Link>
                  )
                })}
              </div>
            </div>
          ))}
        </nav>
      </div>

      {/* Mobile menu button */}
      <div className="lg:hidden fixed top-4 left-4 z-50">
        <button
          onClick={() => setSidebarOpen(true)}
          className="p-2 rounded-md bg-primary-500 text-white shadow-lg"
        >
          <Menu className="w-5 h-5" />
        </button>
      </div>

      {/* Main content */}
      <div className="flex-1 flex flex-col overflow-hidden">
        <main className="flex-1 overflow-y-auto">
          {children}
        </main>
      </div>

      {/* Overlay for mobile */}
      {sidebarOpen && (
        <div
          className="fixed inset-0 z-40 bg-gray-600 bg-opacity-75 lg:hidden"
          onClick={() => setSidebarOpen(false)}
        />
      )}
    </div>
  )
} 