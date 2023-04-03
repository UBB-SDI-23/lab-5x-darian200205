import { useState } from 'react';
import './App.css';
import SearchAppBar from './components/Appbar'
import StudentTable from './components/StudentList';

function App() {

  const [searchText, setSearchText] = useState("");

  return (
    <div className="App">
      <SearchAppBar setSearchText={setSearchText} />
      <StudentTable searchText={searchText} />
    </div>
  );
}
 
export default App;
