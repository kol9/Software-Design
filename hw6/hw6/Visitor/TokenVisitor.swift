//
//  TokenVisitor.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 05.12.2021.
//

import Foundation

public protocol TokenVisitor {
    func visit(_ token: ScalarToken)
    func visit(_ token: ExtraToken)
    func visit(_ token: OperationToken)
}
