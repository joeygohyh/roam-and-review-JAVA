import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Message } from '../../models/models';
import { WebSocketService } from '../../services/web-socket.service';

@Component({
  selector: 'app-meet',
  templateUrl: './meet.component.html',
  styleUrl: './meet.component.css',
})

export class MeetComponent implements OnInit {
  msgs: Message[] = [];
  primeNgMessages: any[] = [];

  form: FormGroup = new FormGroup({
    name: new FormControl<string>('', Validators.required),
    content: new FormControl<string>('', Validators.required),
  });

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit(): void {
    this.webSocketService.listen((msg) => {
      this.msgs.push(msg);
      this.updatePrimeNgMessages();
    });
  }

  add(name: string, content: string): void {
    const msg: Message = {
      name: name,
      content: content,
      date: new Date(),
    };
    this.webSocketService.send(msg);
    console.log(msg);
  }

  click(): void {
    this.add(this.form.value.name, this.form.value.content);
    this.form.reset({});
  }

  private updatePrimeNgMessages(): void {
    this.primeNgMessages = this.msgs.map((msg) => ({
      severity: 'secondary', 
      summary: `${msg.name}:`,
      icon: 'pi pi-user',
      detail: `${msg.content} --------- [${msg.date}]`, 

      escape: false // Allow HTML tags to be rendered

    }));
  }
}
