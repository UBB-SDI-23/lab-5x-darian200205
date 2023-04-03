import * as React from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { Container } from '@mui/system';
import { Paper } from '@mui/material';
import Button from '@mui/material/Button';


export default function StudentForm({setModalState, student, setStudent}) {
    const paperStyle = {padding: '50px 20px', width: 600, margin: '20px auto'}
    const[firstName, setFirstName] = React.useState(student.firstName)
    const[lastName, setLastName] = React.useState(student.lastName)
    const[age, setAge] = React.useState(student.age)
    const[year, setYear] = React.useState(student.year)
    const[email, setEmail] = React.useState(student.email)
    const[openModal, setOpenModal] = React.useState(false);


    const handleAdd = (e) => {
        const student = {firstName, lastName, age, year, email}
        fetch(
            "http://localhost:8080/student/add", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(student)
            }
        ).then(
            () => {
                console.log("Student added")
                setModalState(false);
            }
        )
    }

    const handleClose = () => {
        setOpenModal(false);
    };

    

    return (
        <Container className="overlay">
            <Paper elevation={3} style={paperStyle}>
                <h1> Add student </h1>
                <Box
                    component="form"
                    sx={{
                    '& > :not(style)': { m: 1 },
                    }}
                    noValidate
                    autoComplete="off"
                    elevation={5}
                >
                    <TextField id="outlined-basic" value={firstName} onChange={(e) => setFirstName(e.target.value)} label="First name" variant="outlined" fullWidth />
                    <TextField id="outlined-basic" value={lastName} onChange={(e) => setLastName(e.target.value)} label="Last name" variant="outlined" fullWidth />
                    <TextField id="outlined-basic" value={age} onChange={(e) => setAge(e.target.value)} label="Age" variant="outlined" fullWidth />
                    <TextField id="outlined-basic" value={year} onChange={(e) => setYear(e.target.value)} label="Year" variant="outlined" fullWidth />

                    <TextField id="outlined-basic" value={student.email} onChange={(e) => setEmail(e.target.value)} label="Email" variant="outlined" fullWidth InputProps={{ readOnly: () => {if(student == '') return false; return true;} }}/>
                    <Button variant="contained" onClick={handleAdd} type="submit">Save</Button>
                    <Button variant="contained" onClick={() => setModalState(false)}>Close</Button>
                </Box>
            </Paper>
           
        </Container>
    );
}
