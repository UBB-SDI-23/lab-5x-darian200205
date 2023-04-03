import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import { Container } from '@mui/system';
import StudentForm from './StudentForm';
import Modal from '@mui/material/Modal';
import SearchAppBar from './Appbar';
import axios from 'axios';
import { TextFields } from '@mui/icons-material';
import InputText from './InputText';
import SearchIcon from '@mui/icons-material/Search';
import { styled, alpha } from '@mui/material/styles';
import InputBase from '@mui/material/InputBase';


const Search = styled('div')(({ theme }) => ({
  position: 'relative',
  borderRadius: theme.shape.borderRadius,
  backgroundColor: alpha(theme.palette.common.white, 0.15),
  '&:hover': {
    backgroundColor: alpha(theme.palette.common.white, 0.25),
  },
  marginLeft: 0,
  width: '100%',
  [theme.breakpoints.up('sm')]: {
    marginLeft: theme.spacing(1),
    width: 'auto',
  },
}));

const SearchIconWrapper = styled('div')(({ theme }) => ({
  padding: theme.spacing(0, 2),
  height: '100%',
  position: 'absolute',
  pointerEvents: 'none',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
}));

const StyledInputBase = styled(InputBase)(({ theme }) => ({
  color: 'inherit',
  '& .MuiInputBase-input': {
    padding: theme.spacing(1, 1, 1, 0),
    // vertical padding + font size from searchIcon
    paddingLeft: `calc(1em + ${theme.spacing(4)})`,
    transition: theme.transitions.create('width'),
    width: '100%',
    [theme.breakpoints.up('sm')]: {
      width: '12ch',
      '&:focus': {
        width: '20ch',
      },
    },
  },
}));


export default function StudentTable({searchText}) {
  const [students, setStudents] = React.useState([]);
  const [originalStudents, setOriginalStudents] = React.useState([]);
  const [error, setError] = React.useState(null);
  const[student, setStudent] = React.useState({});

  const [modalState, setModalState] = React.useState(false);
  const [text, setText] = React.useState('');
  const [isEditButton, setIsEditButton] = React.useState(false);

  
  React.useEffect(() => {
    fetch('http://localhost:8080/student/list')
      .then((res) => res.json())
      .then((result) => {
        setStudents(result);
        setOriginalStudents(result);
      })
      .catch((err) => {
        setError(err);
      });
  }, []);
  


  if (error) {
    return <div>Error fetching data</div>;
  }

  const handleInputChange = (newText) => {
    setText(newText);
  };

  const handleButton = (student) => {
    setIsEditButton(true); 
    setModalState(true);
    setStudent(student);
  }

  const handleFilterChange = (event) => {
    const value = event.target.value;
    if(value.length == 0) {
      setStudents(originalStudents);
    } else {
      const filteredStudents = students.filter(student => {
        const firstNameMatch = student.firstName.toLowerCase().includes(value.toLowerCase());
        const lastNameMatch = student.lastName.toLowerCase().includes(value.toLowerCase());
        const emailMatch = student.email.toLowerCase().includes(value.toLowerCase());
        return firstNameMatch || lastNameMatch || emailMatch;
      });
      setStudents(filteredStudents);
    }
  }


  const handleDeleteButton = (student) => {
      fetch(
        "http://localhost:8080/student/delete", {
            method: "DELETE",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(student)
        }
    ).then(
        () => {
            console.log("Student deleted")
            setModalState(false);
        }
    )
  }

  return (
    <Container>
      
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>First name</TableCell>
              <TableCell align="right">Last name</TableCell>
              <TableCell align="right">Age</TableCell>
              <TableCell align="right">Year</TableCell>
              <TableCell align="right">Email</TableCell>
              <TableCell align="right">Edit</TableCell>
              <TableCell align="right">Delete</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {students.map((student) => (
              <TableRow
                key={student.email}
                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
              >
                <TableCell component="th" scope="row">
                  {student.firstName}
                </TableCell>
                <TableCell align="right">{student.lastName}</TableCell>
                <TableCell align="right">{student.age}</TableCell>
                <TableCell align="right">{student.year}</TableCell>
                <TableCell align="right">{student.email}</TableCell>
                <TableCell align="right">
                    <Button variant="contained" onClick={() => handleButton(student)} type='submit'>Edit</Button>
                </TableCell>
                <TableCell>
                  <Button variant="contained" onClick={() => handleDeleteButton(student)} type='submit'>Delete</Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Button variant="contained" onClick={() => {handleButton('')}}>Add student</Button>

      {modalState !== undefined && modalState !== null && (
  modalState && 
    <Modal
      open={() => setModalState(true)}
      onClose={() => setModalState(false)}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description">

        <StudentForm setModalState = {setModalState} student = {student} setStudent = {setStudent} />

    </Modal>
)}

<Search>
            <SearchIconWrapper>
              <SearchIcon />
            </SearchIconWrapper>
            <StyledInputBase
              placeholder="Search…"
              inputProps={{ 'aria-label': 'search' }}
              onChange={handleFilterChange}
            />
          </Search>
    </Container>
  );
}
