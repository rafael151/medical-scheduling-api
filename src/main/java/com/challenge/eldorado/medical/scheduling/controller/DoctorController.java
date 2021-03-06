package com.challenge.eldorado.medical.scheduling.controller;

import com.challenge.eldorado.medical.scheduling.controller.dto.DoctorDto;
import com.challenge.eldorado.medical.scheduling.models.Doctor;
import com.challenge.eldorado.medical.scheduling.security.Authorized;
import com.challenge.eldorado.medical.scheduling.usecase.DoctorDelete;
import com.challenge.eldorado.medical.scheduling.usecase.DoctorFind;
import com.challenge.eldorado.medical.scheduling.usecase.DoctorSave;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

	private final DoctorSave doctorSave;

	private final DoctorFind doctorFind;

	private final DoctorDelete doctorDelete;

	@Authorized
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Doctor save(@Valid @RequestBody final DoctorDto doctorDto) {
		return doctorSave.save(doctorDto.toModel());
	}

	@Authorized
	@GetMapping
	public List<Doctor> findAll() {
		return doctorFind.findAll();
	}

	@Authorized
	@GetMapping("/{id}")
	public Doctor findById(@PathVariable final UUID id) {
		return doctorFind.findById(id);
	}

	@Authorized
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable final UUID id, @RequestBody final DoctorDto doctorDto) {
		final var doctor = doctorFind.findById(id);
		doctorSave.save(doctorDto.toModel(doctor));
	}

	@Authorized
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable final UUID id) {
		doctorDelete.delete(id);
	}
}
