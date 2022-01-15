import { Button } from "antd";
import { getAllStudents } from "./client";
import './App.css';

function App() {
    getAllStudents()
        .then(res => res.json())
        .then(console.log)
  return (
    <div className="App">
      <Button type="primary">Hello</Button>
    </div>
  );
}

export default App;
