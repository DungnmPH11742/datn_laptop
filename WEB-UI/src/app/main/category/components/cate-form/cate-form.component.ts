import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { CategoryService } from '../../service/category.service';

@Component({
  selector: 'app-cate-form',
  templateUrl: './cate-form.component.html',
  styles: []
})
export class CateFormComponent implements OnInit {
  form: FormGroup;

  constructor(private cateService: CategoryService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.createForm();
  }

  createForm() {
    this.form = this.fb.group({
      id: '',
      name: [''],
      parentId: 0,
      actived: true
    });
    this.form.get('actived')?.setValue(true);
  }
}
