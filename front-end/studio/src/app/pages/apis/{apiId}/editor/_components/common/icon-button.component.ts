/**
 * @license
 * Copyright 2017 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    EventEmitter,
    Input,
    Output,
    ViewEncapsulation
} from "@angular/core";
import {AbstractBaseComponent} from "./base-component";
import {DocumentService} from "../../_services/document.service";
import {CommandService} from "../../_services/command.service";
import {SelectionService} from "../../_services/selection.service";


@Component({
    moduleId: module.id,
    selector: "icon-button",
    templateUrl: "icon-button.component.html",
    styleUrls: ["icon-button.component.css"],
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class IconButtonComponent extends AbstractBaseComponent {

    @Input() type: string;
    @Input() disabled: boolean = false;
    @Input() title: string;
    @Input() invisible: boolean = false;
    @Input() pullRight: boolean = false;
    @Output() onClick: EventEmitter<void> = new EventEmitter<void>();

    constructor(private changeDetectorRef: ChangeDetectorRef, private documentService: DocumentService,
                private selectionService: SelectionService) {
        super(changeDetectorRef, documentService, selectionService);
    }

    public pfIconType(): string {
        if (this.type === 'add') {
            return "pficon pficon-add-circle-o";
        }
        if (this.type === 'edit') {
            return "pficon pficon-edit";
        }
        if (this.type === 'delete' || this.type === 'remove') {
            return "fa fa-trash";
        }
        if (this.type === 'import') {
            return "pficon pficon-import";
        }
        return "fa fa-info";
    }

    public doClick(): void {
        this.onClick.emit();
    }

}
